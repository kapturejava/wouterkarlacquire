package be.kapture.tdd.wouterkarl.acquire;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class Player {
    public static final int MAX_NUMBER_OF_TILES = 6;
    private List<Coordinates> tiles = new ArrayList<>();
    private int money = 0;
    private Map<Company, Integer> shares = new EnumMap<>(Company.class);

    private Player(List<Coordinates> tiles, int money, Map<Company, Integer> shares) {
        this.tiles = ImmutableList.copyOf(tiles);
        this.money = money;
        this.shares = ImmutableMap.copyOf(shares);
    }

    public Player() {
    }

    Player addTile(Coordinates coordinates) {
        checkNotNull(coordinates);
        checkArgument(!tiles.contains(coordinates));
        checkState(tiles.size() < MAX_NUMBER_OF_TILES);

        List<Coordinates> newTiles = new ArrayList<>(tiles);
        newTiles.add(coordinates);

        return new Player(newTiles, money, shares);
    }

    public List<Coordinates> getTiles() {
        return tiles;
    }

    public Player removeTile(Coordinates coordinates) {
        checkNotNull(coordinates);
        checkArgument(tiles.contains(coordinates));

        List<Coordinates> newTiles = new ArrayList<>(tiles);
        newTiles.remove(coordinates);

        return new Player(newTiles, money, shares);
    }

    public boolean hasSufficientTiles() {
        return tiles.size() == MAX_NUMBER_OF_TILES;
    }

    public Player addMoney(int toAdd) {
        checkArgument(validateMoney(toAdd));

        return new Player(tiles, money + toAdd, shares);
    }

    public int getMoney() {
        return money;
    }


    public Player addSharesUsingMoney(List<Company> companies, int moneyToRemove) {
        checkArgument(isNotEmpty(companies));
        checkArgument(validateMoney(moneyToRemove));

        Map<Company, Integer> newShares = new HashMap<>(this.shares);
        for (Company company : companies) {
            newShares.merge(company, 1, Integer::sum);
        }

        return new Player(this.tiles, this.money - moneyToRemove, newShares);
    }

    private boolean validateMoney(int toAdd) {
        return toAdd % 100 == 0 && toAdd >= 100;
    }

    public Map<Company, Integer> getShares() {
        return this.shares;
    }


    public Player sellShares(Company company, int nr, int pricePerShare) {
        if (company == null || nr <= 0 || !validateMoney(pricePerShare)) {
            throw new IllegalArgumentException();
        }
        if (shares.getOrDefault(company, 0) < nr) {
            throw new IllegalArgumentException();
        }

        Map<Company, Integer> newShares = new HashMap<>(shares);
        newShares.merge(company, nr * -1, Integer::sum);

        return new Player(this.tiles, this.money + (nr * pricePerShare), newShares);
    }

    public Player swapShares2to1(Company companySmall, Company companyBig, int nrSharesSmallCompany) {
        if (companySmall == null || companyBig == null || nrSharesSmallCompany % 2 != 0 || nrSharesSmallCompany < 0) {
            throw new IllegalArgumentException();
        }
        if (companyBig == companySmall) {
            throw new IllegalArgumentException();
        }
        if(nrSharesSmallCompany > shares.getOrDefault(companySmall,0))
            throw new IllegalArgumentException();

        Map<Company, Integer> newShares = new HashMap<>(this.shares);
        newShares.put(companyBig, nrSharesSmallCompany / 2);
        putOrRemoveIf0(newShares, companySmall, nrSharesSmallCompany);

        return new Player(tiles, money, newShares);
    }

    private void putOrRemoveIf0(Map<Company, Integer> newShares, Company companySmall, int nrSharesSmallCompany) {
        if (newShares.get(companySmall) == nrSharesSmallCompany) {
            newShares.remove(companySmall);
        } else {
            newShares.merge(companySmall, nrSharesSmallCompany * -1, Integer::sum);
        }
    }


}
