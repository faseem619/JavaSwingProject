import java.util.ArrayList;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class game {
    char board[];
    char CurrentWinner;

    public static void main (String[] args){
        game G = new game();
        HumanPlayer X = new HumanPlayer('X');
        AIPlayer O = new AIPlayer('O');
        MyFrame f=new MyFrame();
        f.setLayout(null);
        JPanel[] panel = new JPanel[4];
        for(int i =0;i<4;i++){
            panel[i]= new JPanel();
            if(i<2){
            panel[i].setBounds((100*(i+1))-5,0,10,300);
            
            }
            else{
                panel[i].setBounds(0,(100*(i-1))-5,300,10);
            }
            panel[i].setBackground(new Color(0x111111));
            f.add(panel[i]);
            
        }
        playAI(G, X, O, true,f);
        
    } 

    public game (){
        this.board= new char[9];
        for ( int i =0; i<9;i++){
            this.board[i] = ' ';

        }
        this.CurrentWinner='\0'; 
    }
    public boolean checkRow(char letter,int row_ind){
        boolean flag= true;
          for(int i =row_ind*3;i< (row_ind*3 +3);i++){
            if(this.board[i]!=letter){
                flag=false;
                break;
                
            }
        }
        return flag;

    }
      public boolean checkColumn(char letter,int col_ind){
        boolean flag= true;
          for(int i =col_ind;i<=col_ind+6;i+=3){
            if(this.board[i]!=letter){
                flag=false;
                break;
                
            }
        }
        return flag;

    }
      public boolean checkDiagonal(char letter){
        boolean flag1= true,flag2=true;
        int [] temp1 = {0,4,8};

        for (int n: temp1){
            if(this.board[n]!=letter){
                flag1=false;
                break;
            }

          }
        int [] temp2 = {2,4,6};

        for (int n: temp2){
            if(this.board[n]!=letter){
                flag2=false;
                break;
            }

          }
        
        return flag1 || flag2;

    }
    public boolean winner(int square,char letter) {

        int row_ind = square/3;
        if(checkRow(letter, row_ind)) return true;
        int col_ind = square%3;
        if(checkColumn(letter, col_ind)) return true;
        if(square%2 ==0)
            if(checkDiagonal(letter)) return true;

        
        return false;

    }
    public  void print_board(){
        int j;
        for (int i =0; i<9;){
            for( j =0; j<3;j++){
                System.out.printf("|%c",this.board[i]);
                i++;
               
            }
             System.out.println("|");
        }
    }
    public  static void print_board_nums(){
        int j;
        for (int i =0; i<9;){
            for( j =0; j<3;j++){
                System.out.printf("|%d",i);
                i++;
               
            }
             System.out.println("|");
        }
    }
    public ArrayList<Integer> availableNums(){
        ArrayList <Integer> moves =new ArrayList <Integer>();
    
        for ( int i = 0; i< this.board.length;i++){
            if (this.board[i] == ' ' ){
                moves.add(i);
            }
        }
        return moves;
    }
    public boolean emptySquares () {
        return this.availableNums().size()>0 ; 
    }
    public int numberOfEmptySquares() {
        return this.availableNums().size() ;
    }
    public boolean makeMove( int square, char letter) {
        if (this.board[square]==' '){
            this.board[square]= letter;
            if(this.winner(square,letter))
                this.CurrentWinner=letter;
            return true;
        }
        return false;
    }
    public  static void play(game G,HumanPlayer xPlayer,RandomComputerPlayer oPlayer,boolean printGame){
        if(printGame){
            print_board_nums();
        }
        char letter = 'X';
        int square;
        Scanner sc= new Scanner(System.in);
        while(G.emptySquares()){
             if (letter=='O'){
                 square= oPlayer.getMove(G);
             }
             else{
                 square= xPlayer.getMoves(G,sc);
             }
             if (G.makeMove( square, letter)){
                if(printGame)
                    G.print_board();
                System.out.printf(" \n %c makes a move to square %d \n",letter,square);
                if(G.CurrentWinner!='\0'){
                    if(printGame)
                        System.out.printf("%c Wins!" ,letter);
                    return ;
                }
                letter = letter=='X'? 'O':'X';
             }
             
        }
        sc.close();
        System.out.println("It's a Tie!");
      
       

    }
    public static JLabel makeJLabel(String s, int square){
        JLabel l1=new JLabel(s);
        if(s=="X")
            l1.setForeground(new Color(0xF11111));
        else l1.setForeground(new Color(0xFFFFFF)); 
        l1.setFont(new Font("Serif", Font.BOLD, 38));
        l1.setBounds((100*(square%3))+36,(100*(square/3))+22,50,50);
        return l1; 

    } 
    public  static void playAI(game G,HumanPlayer xPlayer,AIPlayer oPlayer,boolean printGame,  JFrame f){
        if(printGame){
            print_board_nums();
        }
        char letter = 'X';
        int square;
       
        Scanner sc= new Scanner(System.in);
        while(G.emptySquares()){
             if (letter=='O'){
                 square= oPlayer.getMoves(G);
                 f.add(makeJLabel("O",square));
                 f.repaint();
             }
             else{
                 square= xPlayer.getMoves(G,sc);
                  f.add(makeJLabel("X",square));
                  f.repaint();
             }
             if (G.makeMove( square, letter)){
                if(printGame)
                    G.print_board();
                System.out.printf(" \n %c makes a move to square %d \n",letter,square);
                if(G.CurrentWinner!='\0'){
                    if(printGame)
                        System.out.printf("%c Wins!" ,letter);
                    return ;
                }
                letter = letter=='X'? 'O':'X';
             }
             
        }
       sc.close();
        System.out.println("It's a Tie!");
          System.exit(0);
       

    }
    
}

class player {
    char letter ;
    public player ( char letter){
        this.letter= letter;

    }
}

class RandomComputerPlayer extends player {

    public RandomComputerPlayer (char letter) {
        super(letter);
    }
    public int  getMove(game G) {

        ArrayList<Integer> temp =  G.availableNums();
        int square = temp.get((int)(Math.random() * temp.size()));
        return square;
    }
}

class HumanPlayer extends player {
    public HumanPlayer (char letter) {
        super(letter);
    }
    public int getMoves(game G,Scanner sc) {

        int square= -1;
        boolean valid = false;
        while(!valid)
        {   
            System.out.printf("%c \'s turn. Input a value between (0-8) \n",super.letter);
            square= sc.nextInt();
            if (G.availableNums().contains(square))
            {
                valid=true;
            }
            else
            {

                System.out.printf("Invalid Input. Try again \n");
            }
                
        }
        return square;

    }



}

class AIPlayer extends player{
    public AIPlayer(char letter){
        super(letter);
    }
    public int getMoves(game G){
         ArrayList<Integer> temp =  G.availableNums();
         int square;
         if (temp.size() == 9){
                square = temp.get((int)(Math.random() * temp.size())); 
         }
         else {
              square = this.minimax(G, this.letter)[0];
         }
            return square;
    }
    private int[] minimax(game G, char player){
        char max_player = this.letter;
        char other_player = player =='X'? 'Y': 'X';

        if(G.CurrentWinner==other_player){
            if(other_player==max_player){
                int [] temp2 ={-1,G.numberOfEmptySquares()+1};
                return temp2 ;

            }
            else {
                int [] temp2 ={-1,-1*(G.numberOfEmptySquares()+1)};
                return temp2;
            }
        }
        else if (G.numberOfEmptySquares()==0){
            return new int[] {-1,0};
        }

        int [] best= player==max_player? new int[] {-1,-9999}: new int[]{-1,9999};

        ArrayList<Integer> temp =  G.availableNums();

        for (Integer move : temp){
            G.makeMove(move, player);
            int [] score  = this.minimax(G, other_player);

            G.board[move]=' ';
            G.CurrentWinner='\0';

            score[0]=move;
            if(player==max_player){
                if (score[1]>best[1])
                {

                    best[1]=score[1];
                    best[0]=score[0];
                }
            }
            else{
                if (score[1]<best[1])
                {

                    best[1]=score[1];
                    best[0]=score[0];
                }

            }


        }
        return best;


    }
}