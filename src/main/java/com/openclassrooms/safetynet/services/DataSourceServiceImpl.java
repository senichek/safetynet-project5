package com.openclassrooms.safetynet.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Medicalrecord;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.PersonFullDetails;
import com.openclassrooms.safetynet.models.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.models.DTO.CoveredByFirestationDTO;
import com.openclassrooms.safetynet.models.DTO.FireDTO;
import com.openclassrooms.safetynet.models.DTO.FloodDTO;
import com.openclassrooms.safetynet.repos.DataRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    DataRepo dataRepo;

    @Override
    public DataSource getAllData() {
        return this.dataRepo.getData();
    }

    @Override
    public CoveredByFirestationDTO getAllPeopleByFirestation(int num) {
        CoveredByFirestationDTO dto = new CoveredByFirestationDTO();
        List<PersonFullDetails> peopleCoveredByFirestation = new ArrayList<>();
        dataRepo.getData().getFirestations().forEach(fStation -> {
            if (fStation.getStation() == num) {
                dataRepo.getData().getPersons().forEach(p -> {
                    if (p.getAddress().equals(fStation.getAddress())) {
                    PersonFullDetails person = new PersonFullDetails();
                    person.setFirstName(p.getFirstName());
                    person.setLastName(p.getLastName());
                    person.setAddress(p.getAddress());
                    person.setCity(p.getCity());
                    person.setZip(p.getZip());
                    person.setPhone(p.getPhone());
                    person.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                    peopleCoveredByFirestation.add(person);
                    }
                });
            }
        });

        int numOfKids = peopleCoveredByFirestation.stream().filter(person -> person.getAge() < 19)
                .collect(Collectors.toList()).size();
        int numOfAdults = peopleCoveredByFirestation.size() - numOfKids;

        dto.setPersons(peopleCoveredByFirestation);
        dto.setNumberOfKids(numOfKids);
        dto.setNumberOfAdults(numOfAdults);
        return dto;
    }

    @Override
    public List<ChildAlertDTO> getChildrenByAddress(String address) {
        List<Person> filteredData = new ArrayList<>();
        // Filtering full collection by address
        filteredData = dataRepo.getData().getPersons().stream().filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
        
        List<ChildAlertDTO> result = new ArrayList<>();
        filteredData.forEach(p -> {
            ChildAlertDTO childAlertDTO = new ChildAlertDTO();
            childAlertDTO.setFirstName(p.getFirstName());
            childAlertDTO.setLastName(p.getLastName());
            // Looking for the kids in the filtered results.
            if (getAgeByPersonName(p.getFirstName(), p.getLastName()) < 19) {
                childAlertDTO.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                childAlertDTO.setMembersOfHoushold(getHousholdMembers(p.getFirstName(), p.getLastName(), p.getAddress()));
                result.add(childAlertDTO);
            }
        });
        return result;
    }

    @Override
    public List<Person> getHousholdMembers(String firstName, String lastName, String address) {
        List<Person> result = new ArrayList<>();
        dataRepo.getData().getPersons().forEach(person -> {
            if (person.getLastName().equals(lastName) && person.getAddress().equals(address)
                    && !person.getFirstName().equals(firstName)) {
                result.add(person);
            }
        });
        return result;
    }

    @Override
    public Set<String> getPhonesByFirestation(Integer fStationNumber) {
        Set<String> result = new HashSet<>();
        dataRepo.getData().getFirestations().forEach(fStation -> {
            if (fStation.getStation() == fStationNumber) {
                dataRepo.getData().getPersons().forEach(p -> {
                    if (p.getAddress().equals(fStation.getAddress())) {
                        result.add(p.getPhone());
                    }
                });
            }
        });
        return result;
    }

    @Override
    public FireDTO getAllByAddressInCaseOfFire(String address) {
        List<PersonFullDetails> peopleByAddress = new ArrayList<>();
        Set<Integer> fireStationsNumbers = new HashSet<>();
        FireDTO result = new FireDTO();
        dataRepo.getData().getPersons().forEach(person -> {
            if (person.getAddress().equals(address)) {
                PersonFullDetails prs = new PersonFullDetails();
                prs.setLastName(person.getLastName());
                prs.setPhone(person.getPhone());
                prs.setAge(getAgeByPersonName(person.getFirstName(), person.getLastName()));
                prs.setMedications(getMedicationsByPersonName(person.getFirstName(), person.getLastName()));
                prs.setAllergies(getAllergiesByPersonName(person.getFirstName(), person.getLastName()));
                peopleByAddress.add(prs);

                dataRepo.getData().getFirestations().forEach(fStation -> {
                    if (fStation.getAddress().equals(address)) {
                        fireStationsNumbers.add(fStation.getStation());
                    }
                });
            }
        });
        result.setPersons(peopleByAddress);
        result.setFireStationNumbers(fireStationsNumbers);
        return result;
    }

    @Override
    public Set<Firestation> getFirestationsByAddress(String address) {
        Set<Firestation> result = new HashSet<>();
        dataRepo.getData().getFirestations().forEach(fStation -> {
            if (fStation.getAddress().equals(address)) {
                result.add(fStation);
            }
        });
        return result;
    }

    @Override
    public LocalDate getBirthdateByName(String fName, String lName) {
        LocalDate birthdate = null;
        for (Medicalrecord medRec : dataRepo.getData().getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                birthdate = medRec.getBirthdate();
            }
        }
        return birthdate;
    }

    @Override
    public String[] getMedicationsByPersonName(String fName, String lName) {
        String[] medications = null;
        for (Medicalrecord medRec : dataRepo.getData().getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                medications = medRec.getMedications();
            }
        }
        return medications;
    }

    @Override
    public String[] getAllergiesByPersonName(String fName, String lName) {
        String[] allegries = null;
        for (Medicalrecord medRec : dataRepo.getData().getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                allegries = medRec.getAllergies();
            }
        }
        return allegries;
    }

    @Override
    public int getAgeByPersonName(String fName, String lName) {
        LocalDate today = LocalDate.now();
        Integer age = null;
        for (Medicalrecord medRec : dataRepo.getData().getMedicalrecords()) {
            if (fName.equals(medRec.getFirstName()) && lName.equals(medRec.getLastName())) {
                age = Period.between(medRec.getBirthdate(), today).getYears();
            }
        }
        return age;
    }

    @Override
    public List<FloodDTO> getAllByFireStationNumberFlood(Set<Integer> fireStationNums) {
        List<FloodDTO> result = new ArrayList<>();
        Set<String> addressesCoveredByFireStations = new HashSet<>();
        // Getting Firestations' addresses
        fireStationNums.forEach(fNum -> {
            dataRepo.getData().getFirestations().forEach(fStation -> {
                if (fNum == fStation.getStation()) {
                    addressesCoveredByFireStations.add(fStation.getAddress());
                }
            });
        });
        // Filtering the people by firestation address
        addressesCoveredByFireStations.forEach(adr -> {
            List<PersonFullDetails> peopleByAddress = new ArrayList<>();
            dataRepo.getData().getPersons().forEach(p -> {
                if (adr.equals(p.getAddress())) {
                    PersonFullDetails prs = new PersonFullDetails();
                    prs.setFirstName(p.getFirstName());
                    prs.setLastName(p.getLastName());
                    prs.setPhone(p.getPhone());
                    prs.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setMedications(getMedicationsByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setAllergies(getAllergiesByPersonName(p.getFirstName(), p.getLastName()));
                    peopleByAddress.add(prs);
                }
            });
            FloodDTO floodDTO = new FloodDTO();
            floodDTO.setAddress(adr);
            floodDTO.setPeople(peopleByAddress);
            result.add(floodDTO);
        });
        return result;
    }

    @Override
    public List<PersonFullDetails> getAllByFirstNameAndLastName(String firstName, String lastName) {
        List<PersonFullDetails> result = new ArrayList<>();
        // Return the full list of people if there are no params in URL
        if (firstName.equals("") && lastName.equals("")) {
            dataRepo.getData().getPersons().forEach(p -> {
                PersonFullDetails prs = new PersonFullDetails();
                prs.setFirstName(p.getFirstName());
                prs.setLastName(p.getLastName());
                prs.setAddress(p.getAddress());
                prs.setCity(p.getCity());
                prs.setZip(p.getZip());
                prs.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                prs.setEmail(p.getEmail());
                prs.setMedications(getMedicationsByPersonName(p.getFirstName(), p.getLastName()));
                prs.setAllergies(getAllergiesByPersonName(p.getFirstName(), p.getLastName()));
                result.add(prs);
            });
        } else if (!firstName.equals("") && lastName.equals("")) {
            dataRepo.getData().getPersons().forEach(p -> {
                if (firstName.equals(p.getFirstName())) {
                    PersonFullDetails prs = new PersonFullDetails();
                    prs.setFirstName(p.getFirstName());
                    prs.setLastName(p.getLastName());
                    prs.setAddress(p.getAddress());
                    prs.setCity(p.getCity());
                    prs.setZip(p.getZip());
                    prs.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setEmail(p.getEmail());
                    prs.setMedications(getMedicationsByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setAllergies(getAllergiesByPersonName(p.getFirstName(), p.getLastName()));
                    result.add(prs);
                }
            });
        } else if (firstName.equals("") && !lastName.equals("")) {
            dataRepo.getData().getPersons().forEach(p -> {
                if (lastName.equals(p.getLastName())) {
                    PersonFullDetails prs = new PersonFullDetails();
                    prs.setFirstName(p.getFirstName());
                    prs.setLastName(p.getLastName());
                    prs.setAddress(p.getAddress());
                    prs.setCity(p.getCity());
                    prs.setZip(p.getZip());
                    prs.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setEmail(p.getEmail());
                    prs.setMedications(getMedicationsByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setAllergies(getAllergiesByPersonName(p.getFirstName(), p.getLastName()));
                    result.add(prs);
                }
            });
        } else {
            dataRepo.getData().getPersons().forEach(p -> {
                if (firstName.equals(p.getFirstName()) && lastName.equals(p.getLastName())) {
                    PersonFullDetails prs = new PersonFullDetails();
                    prs.setFirstName(p.getFirstName());
                    prs.setLastName(p.getLastName());
                    prs.setAddress(p.getAddress());
                    prs.setCity(p.getCity());
                    prs.setZip(p.getZip());
                    prs.setAge(getAgeByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setEmail(p.getEmail());
                    prs.setMedications(getMedicationsByPersonName(p.getFirstName(), p.getLastName()));
                    prs.setAllergies(getAllergiesByPersonName(p.getFirstName(), p.getLastName()));
                    result.add(prs);
                }
            });
        }
        return result;
    }

    @Override
    public Set<String> getEmailsByCityName(String city) {
        Set<String> result = new HashSet<>();
        if (city == null || city.equals("")) {
            return null;
        } else {
            dataRepo.getData().getPersons().forEach(p -> {
                if (p.getCity().toLowerCase().equals(city.toLowerCase())) {
                    result.add(p.getEmail());
                }
            });
            return result;
        }
    }
}
