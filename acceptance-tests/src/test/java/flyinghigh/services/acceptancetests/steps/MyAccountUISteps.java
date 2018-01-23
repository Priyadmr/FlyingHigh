package flyinghigh.services.acceptancetests.steps;

import flyinghigh.services.acceptancetests.pages.MyAccountPage;
import net.thucydides.core.annotations.Step;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MyAccountUISteps {

    MyAccountPage myAccountPage;

    @Step
    public void openAccountPage() {
        myAccountPage.open();
        myAccountPage.waitForFieldsToLoad();
    }

    @Step
    public int calculatePointsNeededBetween(String departure,
                                            String destination) {
        myAccountPage.selectDepartureCity(departure);
        myAccountPage.selectDestinationCity(destination);
        myAccountPage.waitForCalulationResult();
        return myAccountPage.getCalculatedPoints();
    }

    @Step
    public void shouldSeeAccountBalanceOf(int expectedPoints) {
        assertThat(myAccountPage.getPointBalance()).isEqualTo(expectedPoints);
    }

    @Step
    public void shouldSeeHomeCity(String expectedHomeCity) {
        assertThat(myAccountPage.getHomeCity()).isEqualTo(expectedHomeCity);
    }

    @Step
    public void shouldSeePossibleDestinations(List<String> expectedAirports) {
        assertThat(myAccountPage.getPossibleDestinations()).containsAll(expectedAirports);
    }
}
