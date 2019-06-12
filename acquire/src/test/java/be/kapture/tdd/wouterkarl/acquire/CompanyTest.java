package be.kapture.tdd.wouterkarl.acquire;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CompanyTest {

    @Test
    void price_CHEAPEST() {
        for (Company company : EnumSet.of(Company.SAXON, Company.ZETA)) {
            assertThat(company.price(2)).isEqualTo(200);
            assertThat(company.price(3)).isEqualTo(300);
            assertThat(company.price(4)).isEqualTo(400);
            assertThat(company.price(5)).isEqualTo(500);
            assertThat(company.price(6)).isEqualTo(600);
            assertThat(company.price(10)).isEqualTo(600);
            assertThat(company.price(11)).isEqualTo(700);
            assertThat(company.price(20)).isEqualTo(700);
            assertThat(company.price(21)).isEqualTo(800);
            assertThat(company.price(30)).isEqualTo(800);
            assertThat(company.price(31)).isEqualTo(900);
            assertThat(company.price(40)).isEqualTo(900);
            assertThat(company.price(41)).isEqualTo(1000);
        }
    }

    @Test
    void price_MIDDLE() {
        for (Company company : EnumSet.of(Company.AMERICA, Company.FUSION, Company.HYDRA)) {
            assertThat(company.price(2)).isEqualTo(300);
            assertThat(company.price(3)).isEqualTo(400);
            assertThat(company.price(4)).isEqualTo(500);
            assertThat(company.price(5)).isEqualTo(600);
            assertThat(company.price(6)).isEqualTo(700);
            assertThat(company.price(10)).isEqualTo(700);
            assertThat(company.price(11)).isEqualTo(800);
            assertThat(company.price(20)).isEqualTo(800);
            assertThat(company.price(21)).isEqualTo(900);
            assertThat(company.price(30)).isEqualTo(900);
            assertThat(company.price(31)).isEqualTo(1000);
            assertThat(company.price(40)).isEqualTo(1000);
            assertThat(company.price(41)).isEqualTo(1100);
        }
    }

    @Test
    void price_EXPENSIVE() {
        for (Company company : EnumSet.of(Company.QUANTUM, Company.PHOENIX)) {
            assertThat(company.price(2)).isEqualTo(400);
            assertThat(company.price(3)).isEqualTo(500);
            assertThat(company.price(4)).isEqualTo(600);
            assertThat(company.price(5)).isEqualTo(700);
            assertThat(company.price(6)).isEqualTo(800);
            assertThat(company.price(10)).isEqualTo(800);
            assertThat(company.price(11)).isEqualTo(900);
            assertThat(company.price(20)).isEqualTo(900);
            assertThat(company.price(21)).isEqualTo(1000);
            assertThat(company.price(30)).isEqualTo(1000);
            assertThat(company.price(31)).isEqualTo(1100);
            assertThat(company.price(40)).isEqualTo(1100);
            assertThat(company.price(41)).isEqualTo(1200);
        }
    }

    @Test
    void price_IllegalInput() {
        assertThatIllegalArgumentException().isThrownBy(() -> Company.SAXON.price(1));
        assertThatIllegalArgumentException().isThrownBy(() -> Company.SAXON.price(-1));
    }
}