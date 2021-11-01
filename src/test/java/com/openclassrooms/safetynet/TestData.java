package com.openclassrooms.safetynet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.models.PersonFullDetails;
import com.openclassrooms.safetynet.models.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.models.DTO.FireDTO;
import com.openclassrooms.safetynet.models.DTO.FloodDTO;

public class TestData {

    // Covered by Firestation Test Data
    static PersonFullDetails person1 = new PersonFullDetails("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", null, null, 76, null, null, null);
    static PersonFullDetails person2 = new PersonFullDetails("Zach", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", null, null, 4, null, null, null);
    static PersonFullDetails person3 = new PersonFullDetails("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", null, null, 36, null, null, null);
    static PersonFullDetails person4 = new PersonFullDetails("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", null, null, 33, null, null, null);
    static PersonFullDetails person5 = new PersonFullDetails("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", null, null, 32, null, null, null);
    public static List<PersonFullDetails> coveredByFirestation_2 = List.of(person5, person4, person3, person2, person1);

    // ChildAlert Test Data
    static Person householdMember_1 = new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
            "841-874-7784", "bstel@email.com");
    static Person householdMember_2 = new Person("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
            "841-874-7784", "ssanw@email.com");
    static ChildAlertDTO kid = new ChildAlertDTO("Kendrik", "Stelzer", 7,
            List.of(householdMember_1, householdMember_2));
    public static List<ChildAlertDTO> kids = List.of(kid);

    // Phone numbers covered by Firestation
    public static Set<String> phonesCoverredByFirestation_2 = Set.of("841-874-7512", "841-874-7878", "841-874-7458",
            "841-874-6513");

    // Fire Test Data
    static PersonFullDetails personFire_1 = new PersonFullDetails(null, "Cooper", null, null, null, "841-874-6874",
            null, null, 27, new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" }, null);

    static PersonFullDetails personFire_2 = new PersonFullDetails(null, "Peters", null, null, null, "841-874-8888",
            null, null, 56, new String[] {}, new String[] {}, null);

    static PersonFullDetails personFire_3 = new PersonFullDetails(null, "Boyd", null, null, null, "841-874-9888",
            null, null, 56, new String[] { "aznol:200mg"}, new String[] { "nillacilan" }, null);

    static List<PersonFullDetails> personsFire = List.of(personFire_1, personFire_2, personFire_3);
    static List<Integer> firestationsNums = List.of(3, 4); // List is used to maintain order.
    public static FireDTO fire_data = new FireDTO(personsFire, new HashSet<>(firestationsNums));

    // Flood Test Data
    static PersonFullDetails personFlood_1 = new PersonFullDetails(null, "Cooper", null, null, null, "841-874-9845",
            null, null, 27, new String[] {}, new String[] {}, null);

    static PersonFullDetails personFlood_2 = new PersonFullDetails(null, "Cooper", null, null, null, "841-874-6874",
            null, null, 27, new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" }, null);

    static PersonFullDetails personFlood_3 = new PersonFullDetails(null, "Peters", null, null, null, "841-874-8888",
            null, null, 56, new String[] {}, new String[] {}, null);

    static PersonFullDetails personFlood_4 = new PersonFullDetails(null, "Boyd", null, null, null, "841-874-9888",
            null, null, 56, new String[] { "aznol:200mg"}, new String[] { "nillacilan" }, null);

    static FloodDTO floodDTO_1 = new FloodDTO("489 Manchester St", List.of(personFlood_1));
    static FloodDTO floodDTO_2 = new FloodDTO("112 Steppes Pl", List.of(personFlood_2, personFlood_3, personFlood_4));
    public static List<FloodDTO> floodDTOs = List.of(floodDTO_1, floodDTO_2);
}
