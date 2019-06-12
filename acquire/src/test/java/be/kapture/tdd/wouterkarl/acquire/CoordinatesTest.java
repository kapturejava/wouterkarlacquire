package be.kapture.tdd.wouterkarl.acquire;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CoordinatesTest {

    @Test
    void create_IllegalInput() {
        assertThatIllegalArgumentException().isThrownBy(() -> Coordinates.create(0, 1));
        assertThatIllegalArgumentException().isThrownBy(() -> Coordinates.create(-1, 1));

        assertThatIllegalArgumentException().isThrownBy(() -> Coordinates.create(10, 1));

        assertThatIllegalArgumentException().isThrownBy(() -> Coordinates.create(1, 0));
        assertThatIllegalArgumentException().isThrownBy(() -> Coordinates.create(1, -1));

        assertThatIllegalArgumentException().isThrownBy(() -> Coordinates.create(1, 13));
    }

    @Test
    void create() {
        assertThat(Coordinates.create(1, 1)
                .toString()).contains("1A");
        assertThat(Coordinates.create(2, 5)
                .toString()).contains("2E");
        assertThat(Coordinates.create(3, 7)
                .toString()).contains("3G");
    }

    @Test
    void distance() {
        assertThat(Coordinates.create(1, 1).distanceToOrigin())
                .isEqualTo(0.);

        assertThat(Coordinates.create(1 + 4, 1 + 3).distanceToOrigin())
                .isEqualTo(Math.sqrt(4 * 4 + 3 * 3));
    }

    @Test
    void isNextTo() {
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2+1,2))).isTrue();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2-1,2))).isTrue();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2,2+1))).isTrue();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2,2-1))).isTrue();

        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2+1,2+1))).isFalse();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2+1,2-1))).isFalse();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2-1,2+1))).isFalse();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2-1,2-1))).isFalse();

        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(2,8))).isFalse();
        assertThat(Coordinates.create(2, 2).isNextTo(Coordinates.create(8,2))).isFalse();
    }
}