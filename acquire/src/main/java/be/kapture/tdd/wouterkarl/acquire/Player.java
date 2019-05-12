package be.kapture.tdd.wouterkarl.acquire;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class Player {
    public static final int MAX_NUMBER_OF_TILES = 6;
    private List<Coordinates> tiles = new ArrayList<>();
    private int money = 0;

    private Player(List<Coordinates> tiles, int money) {
        this.tiles = ImmutableList.copyOf(tiles);
        this.money = money;
    }

    public Player() {
    }

    Player addTile(Coordinates coordinates) {
        checkNotNull(coordinates);
        checkArgument(!tiles.contains(coordinates));
        checkState(tiles.size() < MAX_NUMBER_OF_TILES);

        List<Coordinates> newTiles = new ArrayList<>(tiles);
        newTiles.add(coordinates);

        return new Player(newTiles, money);
    }

    public List<Coordinates> getTiles() {
        return tiles;
    }

    public Player removeTile(Coordinates coordinates) {
        checkNotNull(coordinates);
        checkArgument(tiles.contains(coordinates));

        List<Coordinates> newTiles = new ArrayList<>(tiles);
        newTiles.remove(coordinates);

        return new Player(newTiles, money);
    }

    public boolean hasSufficientTiles() {
        return tiles.size() == MAX_NUMBER_OF_TILES;
    }

    public Player addMoney(int toAdd) {
        checkArgument(validateMoney(toAdd));

        return new Player(tiles, money+toAdd);
    }

    private boolean validateMoney(int toAdd) {
        return toAdd % 100 == 0 && toAdd >=100;
    }

    public int getMoney() {
        return money;
    }


    public Player addSharesUsingMoney(List<Company> companies, int moneyToRemove){
        checkArgument(isNotEmpty(companies));
        checkArgument(validateMoney(moneyToRemove));
        throw new UnsupportedOperationException("implement me");
    }
}
