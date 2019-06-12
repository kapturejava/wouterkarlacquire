package be.kapture.tdd.wouterkarl.acquire;

import org.junit.jupiter.api.Test;


import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

class PlayerTest {

    private static final Coordinates COORDINATES_A2 = Coordinates.create(1, 2);
    private static final Coordinates COORDINATES_B2 = Coordinates.create(2, 2);

    @Test
    void addTile_WrongInput() {
        assertThatNullPointerException().isThrownBy(() -> new Player().addTile(null));
    }

    @Test
    void addTile() {
        Player player = new Player().addTile(COORDINATES_A2);
        assertThatIllegalArgumentException().isThrownBy(() -> player.addTile(COORDINATES_A2));
    }

    @Test
    void addTile_Immutable() {
        Player player_1Tile = new Player().addTile(COORDINATES_A2);
        player_1Tile.addTile(COORDINATES_B2);

        assertThat(player_1Tile.getTiles()).doesNotContain(COORDINATES_B2);
    }

    @Test
    void addTile_NotMoreThan6Tiles() {
        Player player_6Tiles = new Player()
                .addTile(Coordinates.create(1, 1))
                .addTile(Coordinates.create(1, 2))
                .addTile(Coordinates.create(1, 3))
                .addTile(Coordinates.create(1, 4))
                .addTile(Coordinates.create(1, 5))
                .addTile(Coordinates.create(1, 6));

        assertThatIllegalStateException().isThrownBy(() -> player_6Tiles.addTile(Coordinates.create(1, 7)));
    }

    @Test
    void removeTile_WrongInput() {
        Player player = new Player().addTile(Coordinates.create(1, 1));

        assertThatNullPointerException().isThrownBy(() -> player.removeTile(null));
    }

    @Test
    void removeTile_OnlyRemoveCoordinateThatisThere() {
        Player player = new Player().addTile(Coordinates.create(1, 1));

        assertThatIllegalArgumentException().isThrownBy(() -> player.removeTile(Coordinates.create(2, 2)));
    }

    @Test
    void removeTile_Immutable() {
        Player player_With11_22 = new Player()
                .addTile(Coordinates.create(1, 1))
                .addTile(Coordinates.create(2, 2));

        Player player_With22 = player_With11_22.removeTile(Coordinates.create(1, 1));
        assertThat(player_With22.getTiles()).doesNotContain(Coordinates.create(1, 1));

        assertThat(player_With11_22.getTiles()).contains(Coordinates.create(1, 1));
    }


    @Test
    void getCoordinates_Immutable() {
        Player player = new Player().addTile(COORDINATES_A2);

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> player.getTiles().add(COORDINATES_B2));
    }

    @Test
    void hasSufficientTiles() {
        Player player_1Tiles = new Player().addTile(Coordinates.create(1, 1));
        assertThat(player_1Tiles.hasSufficientTiles()).isFalse();

        Player player_2Tiles = player_1Tiles.addTile(Coordinates.create(1, 2));
        assertThat(player_2Tiles.hasSufficientTiles()).isFalse();

        Player player_3Tiles = player_2Tiles.addTile(Coordinates.create(1, 3));
        assertThat(player_3Tiles.hasSufficientTiles()).isFalse();

        Player player_4Tiles = player_3Tiles.addTile(Coordinates.create(1, 4));
        assertThat(player_4Tiles.hasSufficientTiles()).isFalse();

        Player player_5Tiles = player_4Tiles.addTile(Coordinates.create(1, 5));
        assertThat(player_5Tiles.hasSufficientTiles()).isFalse();

        Player player_6Tiles = player_5Tiles.addTile(Coordinates.create(1, 6));
        assertThat(player_6Tiles.hasSufficientTiles()).isTrue();
    }


    @Test
    void addMoney_WrongInput() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Player().addMoney(-1));
        assertThatIllegalArgumentException().isThrownBy(() -> new Player().addMoney(50));
        assertThatIllegalArgumentException().isThrownBy(() -> new Player().addMoney(4));
    }

    @Test
    void addMoney() {
        Player player100 = new Player().addMoney(100);

        assertThat(player100.getMoney()).isEqualTo(100);

        assertThat(player100.addMoney(100).getMoney()).isEqualTo(100 + 100);
        assertThat(player100.getMoney()).isEqualTo(100);
    }

    @Test
    void addSharesUsingMoney_WrongInput() {
        Player player_Money300 = new Player().addMoney(400);

        assertThatIllegalArgumentException().isThrownBy(() -> player_Money300.addSharesUsingMoney(null, 300));
        assertThatIllegalArgumentException().isThrownBy(() -> player_Money300.addSharesUsingMoney(emptyList(), 300));

        assertThatIllegalArgumentException().isThrownBy(() -> player_Money300.addSharesUsingMoney(asList(Company.QUANTUM), -1));
        assertThatIllegalArgumentException().isThrownBy(() -> player_Money300.addSharesUsingMoney(asList(Company.QUANTUM), 10));
        assertThatIllegalArgumentException().isThrownBy(() -> player_Money300.addSharesUsingMoney(asList(Company.QUANTUM), 50));
    }


    //TODO only 3, not more then 25
    @Test
    void addSharesUsingMoney() {
        Player player = new Player().addMoney(1700);

        Player playerWithShares = player.addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA, Company.AMERICA), 600);

        assertThat(playerWithShares.getMoney()).isEqualTo(1700 - 600);
        assertThat(playerWithShares.getShares()).contains(entry(Company.AMERICA, 3));

        assertThat(player.getMoney()).isEqualTo(1700);
        assertThat(player.getShares()).isEmpty();

        Player playerWithShares2 = playerWithShares.addSharesUsingMoney(asList(Company.QUANTUM, Company.QUANTUM, Company.QUANTUM), 900);

        assertThat(playerWithShares2.getMoney()).isEqualTo(1700 - 600 - 900);
        assertThat(playerWithShares2.getShares())
                .contains(entry(Company.AMERICA, 3))
                .contains(entry(Company.QUANTUM, 3));

        assertThat(playerWithShares.getMoney()).isEqualTo(1700 - 600);
        assertThat(playerWithShares.getShares()).contains(entry(Company.AMERICA, 3));

        assertThat(player.getMoney()).isEqualTo(1700);
        assertThat(player.getShares()).isEmpty();

    }

    @Test
    void getShares_Immutable() {
        Player player = new Player().addMoney(1700);

        Player playerWithShares = player.addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA, Company.AMERICA), 600);

        assertThatExceptionOfType(Exception.class).isThrownBy(() ->
                playerWithShares.getShares().put(Company.QUANTUM, 3));
    }

    @Test
    void sellShares_IllegalInput() {
        Player player = new Player().addMoney(1700).addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA), 900);

        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(null, 1, 200));

        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, 0, 200));
        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, -1, 200));

        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, 1, 0));
        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, 1, -1));

        IntStream.range(-1, 100).forEach(pricePerShare -> assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, 1, pricePerShare)));
        IntStream.range(101, 200).forEach(pricePerShare -> assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, 1, pricePerShare)));
    }

    @Test
    void sellShares_InvalidForPlayer() {
        Player player = new Player().addMoney(1700).addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA), 900);

        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.FUSION, 1, 200));
        assertThatIllegalArgumentException().isThrownBy(() -> player.sellShares(Company.AMERICA, 2 + 1, 200));


    }

    @Test
    void sellShares() {
        Player player = new Player().addMoney(1700).addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA), 900);

        Player playerAfterSell = player.sellShares(Company.AMERICA, 1, 200);

        assertThat(playerAfterSell.getMoney()).isEqualTo(1700 - 900 + 200);
        assertThat(playerAfterSell.getShares()).containsExactly(entry(Company.AMERICA, 2 - 1));

        assertThat(player.getMoney()).isEqualTo(1700 - 900);
        assertThat(player.getShares()).containsExactly(entry(Company.AMERICA, 2));
    }

    @Test
    void swapShares_IllegalInput() {
        Player player = new Player().addMoney(1700).addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA, Company.AMERICA, Company.AMERICA), 900);

        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(null, Company.QUANTUM, 2));
        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, null, 2));
        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, Company.AMERICA, 2));

        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, Company.QUANTUM, -1));
        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, Company.QUANTUM, -2));
        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, Company.QUANTUM, 1));
        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, Company.QUANTUM, 3));
    }


    @Test
    void swapShares() {
        Player player = new Player().addMoney(1700).addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA, Company.AMERICA, Company.AMERICA), 900);

        Player playerAfterSwap = player.swapShares2to1(Company.AMERICA, Company.FUSION, 4);

        assertThat(playerAfterSwap.getMoney()).isEqualTo(player.getMoney());
        assertThat(playerAfterSwap.getShares()).containsExactly(entry(Company.FUSION, 4 / 2));

        assertThat(player.getShares()).containsExactly(entry(Company.AMERICA, 4));
    }

    @Test
    void swapShares_InvalidInputForPlayer() {
        Player player = new Player().addMoney(1700).addSharesUsingMoney(asList(Company.AMERICA, Company.AMERICA, Company.AMERICA, Company.AMERICA), 900);

        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.AMERICA, Company.FUSION, 6));
        assertThatIllegalArgumentException().isThrownBy(() -> player.swapShares2to1(Company.FUSION, Company.PHOENIX, 6));


    }
}