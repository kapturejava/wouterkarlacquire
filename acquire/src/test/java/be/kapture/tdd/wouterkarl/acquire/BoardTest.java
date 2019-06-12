package be.kapture.tdd.wouterkarl.acquire;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {

    private static final Coordinates COORDINATES_22 = Coordinates.create(2, 2);
    private static final Coordinates COORDINATES_33 = Coordinates.create(3, 3);
    private static final Coordinates COORDINATES_44 = Coordinates.create(4, 4);

    @Test
    void addTile_InvalidInput() {
        assertThatNullPointerException().isThrownBy(() -> new Board().addTile(null));

        Board board = new Board().addTile(COORDINATES_22);
        assertThatIllegalArgumentException().isThrownBy(() -> board.addTile(COORDINATES_22));
    }

    @Test
    void addTile_Immutable() {
        Board board = new Board().addTile(COORDINATES_22);
        board.addTile(COORDINATES_33);
        board.addTile(COORDINATES_33);
    }

    @Test
    void startCompany() {
        Board board_With22_44 = new Board().addTile(COORDINATES_22).addTile(COORDINATES_44);

        Board boardWithFusion = board_With22_44.startCompany(Coordinates.create(2, 3), Company.FUSION);

        Board boardWithFusion_America = boardWithFusion.startCompany(Coordinates.create(4, 3), Company.AMERICA);

        assertThat(boardWithFusion_America.getCompanies()).containsExactly(Company.AMERICA, Company.FUSION);
    }

    @Test
    void startCompany_DoNotReuseCoordinatesToCreateCompany() {
        Board board_With22Incompanie = new Board().addTile(COORDINATES_22).startCompany(Coordinates.create(2, 3), Company.FUSION);

        assertThatIllegalArgumentException().isThrownBy(() -> board_With22Incompanie.startCompany(Coordinates.create(2, 3), Company.AMERICA));
    }

    @Test
    void startCompany_MustBeNeighbourToFreeTile() {
        Board board_With22 = new Board().addTile(COORDINATES_22);

        assertThatIllegalArgumentException().isThrownBy(() -> board_With22.startCompany(Coordinates.create(2, 4), Company.AMERICA));
    }

    @Test
    void startCompany_NotNextToExistingCompany() {
        Board board_WithFussion = new Board().addTile(COORDINATES_22).addTile(Coordinates.create(4,2)).startCompany(Coordinates.create(2, 3), Company.FUSION);

        assertThatIllegalArgumentException().isThrownBy(() -> board_WithFussion.startCompany(Coordinates.create(3,2), Company.AMERICA));
    }

    @Test
    void startCompany_Immutable() {
        Board board_With22 = new Board().addTile(COORDINATES_22);

        board_With22.startCompany(Coordinates.create(2, 3), Company.FUSION);
        board_With22.startCompany(Coordinates.create(2, 3), Company.FUSION);


    }
}