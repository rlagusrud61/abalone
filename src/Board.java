public class Board {

    public static void main(String[] args) {
        System.out.println( "Hello" );
    }


    int[] row1 = new int[] {1,2,3,4,5};
    int[] row2 = new int[] {6,7,8,9,10,11};
    int[] row3 = new int[] {12,13,14,15,16,17,18};
    int[] row4 = new int[] {19,20,21,22,23,24,25,26};
    int[] row5 = new int[] {27,28,29,30,31,32,33,34,35};
    int[] row6 = new int[] {36,37,38,39,40,41,42,43};
    int[] row7 = new int[] {44,45,46,47,48,49,50};
    int[] row8 = new int[] {51,52,53,54,55,56};
    int[] row9 = new int[] {57,58,59,60,61};



    private int[] size = new int[] {5,6,7,8,9,8,7,6,5};



    public int convertToInt(int[] cord) {
        int result = 0;
        for (int i = 0; i < cord[0]; i++) {
            result += size[i];
        }
        result += cord[2] + 1;
        return result;
    }



    public int[] convertToArray(int a) {
        int col = a;
        int row = 0;
        for(int i = 0; i < 9 ; i++){
          if (col > size[i]) {
              col = col - size[i];
              row = i;
          }
        }
        int[] result = new int[] {row, col};
        return result;
    }
}