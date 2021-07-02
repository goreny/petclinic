#•= As a user, I want to add a new owner.
#•= As a user, I want to update an existing owner.
#•= As a user, I want to search for an existing owner by his/her lastname.
#•= As a user, I want to view the list of veterinarians.
#= As a user, I want to add a new pet for an existing owner.
#• As a user, I want to add a visit to the veterinarian for my pet.
#•= As a user, I want to view my pets & visits details.
#•= As a user, I should see a pet clinic logo image on home page below the welcome text.


@petClinic
Feature: Pet Clinic UI tests

  @petClinic.s1
  Scenario Outline: As a user, I want to add a new owner.
    Given the user is on the owner page page
    When all owner details are added
    Then the owner is successfully added:
      | firstName   | lastName   | address   | city   | telephone   |
      | <firstName> | <lastName> | <address> | <city> | <telephone> |

    Examples:
      | firstName | lastName | address          | city    | telephone  |
      | John      | Doe      | 8 Alexandra Road | Newbury | 0775123422 |

  @petClinic.s2
  Scenario: As a user, I want to search for an existing owner by his/her lastname.
    Given the user is on the Find Owner page page
    When the user searches for lastname "Doe"
    Then the owner is displayed