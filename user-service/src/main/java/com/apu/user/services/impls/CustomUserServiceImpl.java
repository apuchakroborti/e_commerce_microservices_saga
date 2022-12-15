package com.apu.user.services.impls;

import com.apu.user.dto.AddressDto;
import com.apu.user.dto.CreateUpdateCustomUserDto;
import com.apu.user.dto.CustomUserDto;
import com.apu.user.dto.request.CustomUserSearchCriteria;
import com.apu.user.entity.Address;
import com.apu.user.entity.Country;
import com.apu.user.entity.Customer;
import com.apu.user.entity.District;
import com.apu.user.exceptions.EmployeeNotFoundException;
import com.apu.user.exceptions.GenericException;
import com.apu.user.repository.CountryRepository;
import com.apu.user.repository.CustomUserRepository;
import com.apu.user.repository.DistrictRepository;
import com.apu.user.security_oauth2.models.security.Authority;
import com.apu.user.security_oauth2.models.security.User;
import com.apu.user.security_oauth2.repository.AuthorityRepository;
import com.apu.user.security_oauth2.repository.UserRepository;
import com.apu.user.services.CustomUserService;
import com.apu.user.specifications.CustomUserSearchSpecifications;
import com.apu.user.utils.Defs;
import com.apu.user.utils.Role;
import com.apu.user.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;

//import static com.apu.employee.utils.ServiceUtil.GENERATE_PAYSLIP_WHILE_JOINING;

@Service
@Transactional
@Slf4j
@RefreshScope
public class CustomUserServiceImpl implements CustomUserService {
    private final CustomUserRepository customUserRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final CountryRepository countryRepository;
    private final DistrictRepository districtRepository;

    @Qualifier("userPasswordEncoder")
    private final PasswordEncoder passwordEncoder;

    @Lazy
    private final RestTemplate template;

    @Value("${microservice.payslip-service.endpoints.endpoint.uri}")
    private String GENERATE_PAYSLIP_WHILE_JOINING;

    @Autowired
    CustomUserServiceImpl(CustomUserRepository customUserRepository,
                          UserRepository userRepository,
                          AuthorityRepository authorityRepository,
                          CountryRepository countryRepository,
                          DistrictRepository districtRepository,
                          @Lazy RestTemplate template,
                          @Qualifier("userPasswordEncoder")PasswordEncoder passwordEncoder){
        this.customUserRepository = customUserRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.template = template;
        this.countryRepository = countryRepository;
        this.districtRepository = districtRepository;
    }


    @Override
    @Transactional
    public User addOauthUser(Customer customer, String password) throws GenericException {
        try {
            log.info("CustomUserServiceImpl::addOauthUser start: email: {}", customer.getEmail());
            Optional<User> optionalUser = userRepository.findByUsername(customer.getEmail());
            if (optionalUser.isPresent()) {
                log.error("CustomUserServiceImpl::addOauthUser user already exists: email: {}", customer.getEmail());
                throw new GenericException(Defs.USER_ALREADY_EXISTS);
            }

            User user = new User();
            user.setUsername(customer.getEmail());
            user.setEnabled(true);

            Authority authority = authorityRepository.findByName(Role.USER.getValue());
            user.setAuthorities(Arrays.asList(authority));
            user.setPassword(passwordEncoder.encode(password));

            user = userRepository.save(user);
            log.debug("CustomUserServiceImpl::addOauthUser user: {}", user.toString());
            log.info("CustomUserServiceImpl::addOauthUser end: email: {}", customer.getEmail());
            return user;
        }catch (GenericException e){
            throw e;
        }catch (Exception e){
            log.error("CustomUserServiceImpl::addOauthUser Exception occurred while adding oauth user email:{} message: {}", customer.getEmail(), e.getMessage());
            throw new GenericException("Error occurred while creating oauth user!");
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CustomUserDto signUpUser(CreateUpdateCustomUserDto createUpdateCustomUserDto) throws GenericException {
        try {
            log.info("CustomUserServiceImpl::signUpUser service start: email: {}", createUpdateCustomUserDto.getEmail());
            Optional<Customer> optionalCustomUser = customUserRepository.findByEmail(createUpdateCustomUserDto.getEmail());
            if (optionalCustomUser.isPresent()){
                log.error("CustomUserServiceImpl::signUpUser service:  userId: {} already exists", createUpdateCustomUserDto.getUserId());
                throw new GenericException(Defs.USER_ALREADY_EXISTS);
            }

            Customer customer = new Customer();
            Utils.copyProperty(createUpdateCustomUserDto, customer);

            if(!createUpdateCustomUserDto.getPassword().equals(createUpdateCustomUserDto.getConfirmPassword())){
                throw new GenericException("Password mismatched!");
            }

            User user = addOauthUser(customer, createUpdateCustomUserDto.getPassword());
            log.info("CustomUserServiceImpl::signUpUser service:  user: {} ", user.toString());

            customer.setOauthUser(user);
            customer.setStatus(true);


            //TODO need to set the addresses
            List<AddressDto> addressDtoList = createUpdateCustomUserDto.getAddressDtoList();
            List<Address> addressList = new ArrayList<>();
            for(AddressDto addressDto: addressDtoList){
                Address address = new Address();
                Utils.copyProperty(addressDto, address);
                Optional<Country> optionalCountry = countryRepository.findById(addressDto.getDistrict().getCountry().getId());
                if(!optionalCountry.isPresent()){
                    throw new GenericException("Country not found!");
                }
                Optional<District> districtOptional = districtRepository.findById(addressDto.getDistrict().getId());
                if(!districtOptional.isPresent()){
                    throw new GenericException("District not found!");
                }
                District district = districtOptional.get();
                address.setDistrict(district);
                addressList.add(address);
            }
            customer.setAddressList(addressList);

            customer.setCreatedBy(1l);
            customer.setCreateTime(LocalDateTime.now());

            customer = customUserRepository.save(customer);

            CustomUserDto customUserDto = new CustomUserDto();
            Utils.copyProperty(customer, customUserDto);
            log.info("CustomUserServiceImpl::signUpUser service end: userId: {} and email: {}", createUpdateCustomUserDto.getUserId(), createUpdateCustomUserDto.getEmail());
            return customUserDto;
        }catch (GenericException e){
            throw e;
        }catch (Exception e){
            log.error("Exception occurred while enrolling employee, message: {}", e.getMessage());
            throw new GenericException(e.getMessage(), e);
        }
    }



    @Override
    public CustomUserDto findByUsername(String username) throws GenericException{
        try {
            log.debug("CustomUserServiceImpl::findByUsername start:  username: {} ", username);
            Optional<Customer> optionalEmployee = customUserRepository.findByEmail(username);
            if (!optionalEmployee.isPresent() || optionalEmployee.get().getStatus().equals(false)) {
                log.debug("CustomUserServiceImpl::findByUsername user not found by  username: {} ", username);
                return null;
            }
            CustomUserDto employeeDto = new CustomUserDto();
            Utils.copyProperty(optionalEmployee.get(), employeeDto);
            log.debug("CustomUserServiceImpl::findByUsername end:  username: {} ", username);
            return employeeDto;
        }catch (Exception e){
            log.error("Error while finding employee by username: {}", username);
            throw new GenericException(e.getMessage());
        }
    }
    @Override
    public CustomUserDto findCustomerById(Long id) throws GenericException{
        try {
            log.info("CustomUserServiceImpl::findEmployeeById start:  id: {} ", id);
            Optional<Customer> optionalUser = customUserRepository.findById(id);

            if (!optionalUser.isPresent() || optionalUser.get().getStatus().equals(false)) {
                log.debug("CustomUserServiceImpl::findEmployeeById employee not found by id: {} ", id);
                throw new EmployeeNotFoundException(Defs.USER_NOT_FOUND);
            }
            CustomUserDto employeeDto = new CustomUserDto();
            Utils.copyProperty(optionalUser.get(), employeeDto);
            log.info("CustomUserServiceImpl::findEmployeeById end: id: {} ", id);
            return employeeDto;

        }catch (Exception e){
            log.error("Error occurred while fetching employee by id: {}", id);
            throw new GenericException("Error occurred while fetching employee by id", e);
        }
    }

    @Override
    public CustomUserDto updateCustomerById(Long id, CustomUserDto employeeDto) throws GenericException{
        try {
            log.debug("CustomUserServiceImpl::updateEmployeeById start:  id: {} ", id);

            Optional<Customer> loggedInEmployee = customUserRepository.getLoggedInEmployee();
            if (loggedInEmployee.isPresent() && !loggedInEmployee.get().getId().equals(id)) {
                throw new GenericException("No permission to up update!");
            }

            Optional<Customer> optionalEmployee = customUserRepository.findById(id);
            if (!optionalEmployee.isPresent() || optionalEmployee.get().getStatus().equals(false)){
                log.debug("CustomUserServiceImpl::updateEmployeeById employee not found by id: {} ", id);
                throw new EmployeeNotFoundException(Defs.USER_NOT_FOUND);
            }


            Customer employee = optionalEmployee.get();
            if (!Utils.isNullOrEmpty(employeeDto.getFirstName())) {
                employee.setFirstName(employeeDto.getFirstName());
            }
            if (!Utils.isNullOrEmpty(employeeDto.getLastName())) {
                employee.setLastName(employeeDto.getLastName());
            }
            employee = customUserRepository.save(employee);
            log.debug("CustomUserServiceImpl::updateEmployeeById employee update successful:  employee: {} ", employee.toString());

            Utils.copyProperty(employee, employeeDto);
            log.debug("CustomUserServiceImpl::updateEmployeeById end:  id: {} ", id);

            return employeeDto;
        }catch (Exception e){
         log.error("Exception occurred while updating employee, id: {}", id);
         throw new GenericException(e.getMessage(), e);
        }
    }

    @Override
    public Page<Customer> getCustomerList(CustomUserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException{
        try {
            log.info("CustomUserServiceImpl::getEmployeeList start:  criteria: {} ", Utils.jsonAsString(criteria));

            Optional<Customer> loggedInEmployee = customUserRepository.getLoggedInEmployee();
            Long id = null;
            if (loggedInEmployee.isPresent()) {
                id = loggedInEmployee.get().getId();
            }

            Page<Customer> userPage = customUserRepository.findAll(
                    CustomUserSearchSpecifications.withId(id == null ? criteria.getId() : id)
                            .and(CustomUserSearchSpecifications.withFirstName(criteria.getFirstName()))
                            .and(CustomUserSearchSpecifications.withLastName(criteria.getLastName()))
                            .and(CustomUserSearchSpecifications.withEmail(criteria.getEmail()))
                            .and(CustomUserSearchSpecifications.withPhone(criteria.getPhone()))
                            .and(CustomUserSearchSpecifications.withStatus(true))
                    , pageable
            );
            log.debug("CustomUserServiceImpl::getEmployeeList number of elements: {} ", userPage.getTotalElements());

            log.info("CustomUserServiceImpl::getEmployeeList end");
            return userPage;
        }catch (Exception e){
            log.error("CustomUserServiceImpl::getEmployeeList exception occurred while fetching user list!");
            throw new GenericException("exception occurred while fetching user list!");
        }
    }

    @Override
    public Boolean deleteCustomerById(Long id) throws GenericException{
        try {
            log.info("CustomUserServiceImpl::deleteEmployeeById start:  id: {} ", id);

            Optional<Customer> loggedInEmployee = customUserRepository.getLoggedInEmployee();
            Optional<Customer> optionalEmployee = customUserRepository.findById(id);
            if (loggedInEmployee.isPresent() && optionalEmployee.isPresent() &&
                    !loggedInEmployee.get().getId().equals(optionalEmployee.get().getId())) {
                throw new GenericException(Defs.NO_PERMISSION_TO_DELETE);
            }
            if (!optionalEmployee.isPresent()) {
                throw new EmployeeNotFoundException(Defs.USER_NOT_FOUND);
            }

            Customer employee = optionalEmployee.get();
            employee.setStatus(false);

            //soft deletion of employee
            employee = customUserRepository.save(employee);
            com.apu.user.security_oauth2.models.security.User user = employee.getOauthUser();
            user.setEnabled(false);
            userRepository.save(user);

            log.info("CustomUserServiceImpl::deleteEmployeeById end:  id: {} ", id);
            return  true;
        } catch (GenericException e){
            throw e;
        }catch (Exception e){
            log.error("Exception occurred while deleting user, user id: {}", id);
            throw new GenericException(e.getMessage(), e);
        }
    }
}
