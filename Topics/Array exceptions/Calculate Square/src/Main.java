class FixingExceptions {

    public static void calculateSquare(int[] array, int index) {
        try {
            System.out.println((int) Math.pow(array[index], 2));
        } catch (Exception e) {
            System.out.println("Exception!");
        }
    }
}