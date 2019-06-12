package be.kapture.tdd.wouterkarl.acquire;

public enum Company {
    SAXON(0), ZETA(0), AMERICA(1), FUSION(1), HYDRA(1), QUANTUM(2), PHOENIX(2);

    public static final int SIZE_PRICE_OFFSET = 100;
    public static final int SIZE_PRIZE = 100;

    private final int diff;

    Company(int diff) {
        this.diff = diff;
    }

    public int price(int size) {
        if (size < 2) {
            throw new IllegalArgumentException();
        }

        if (size > 5 ) {
            return (diff * SIZE_PRICE_OFFSET) +((size-1)/10) * SIZE_PRIZE + 600;
        }

        return (diff * SIZE_PRICE_OFFSET ) + size * SIZE_PRIZE;
    }
}
