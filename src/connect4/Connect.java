package connect4;
import sac.game.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Connect extends GameStateImpl {
    public static final int m = 8;    
    public static final int n = 8;      
    public static final byte E=0;
    public static final byte X=1;
    public static final byte O=2;

    int ileX = 0;
    int ileO = 0;

    int wynik1 = 0;
    int wynik2 = 0;


    private byte[][] board = null;
	private static Scanner scan;

    public Connect(){
        board = new byte[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n;j++){
                board[i][j] = E;
            }
        }
    }

    public Connect(Connect parent){

        board = new byte[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n;j++){
                board[i][j] = parent.board[i][j];
            }
        }
        setMaximizingTurnNow(parent.isMaximizingTurnNow());
    }

     public byte[][] getBoard() {
        return board;
    }


    @Override
    public List<GameState> generateChildren() {

        List<GameState> children = new ArrayList<GameState>();

        for(int j=0; j<n;j++){
            Connect child = new Connect(this);
            if(child.make_move(j)){
                children.add(child);
                child.setMoveName(Integer.toString(j));
            }
        }

        return children;
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();

        txt.append("\n");
        for(int i=0; i<m; i++){
            txt.append("| ");
            for(int j=0; j<n; j++){

                if(board[i][j]==E){
                    txt.append("  | ");

                }else if(board[i][j]==X){
                    txt.append("x | ");

                }else if(board[i][j]==O){
                    txt.append("O | ");
                }
            }
            txt.append("\n");
        }       
        txt.append("  ");
        for(int i=0; i<n; i++){
            txt. append(i +"   ");
        }
        return txt.toString();
    }

    public boolean make_move(int kolumna){
        boolean result = false;
        for(int i=m-1; i>=0; i--){
            if(board[i][kolumna] == E){
                board[i][kolumna] = (isMaximizingTurnNow()) ? O : X; // bylo-  is? X:O;
                //is_win();
                result = true;
                break;
            }
        }
        if(result){
            setMaximizingTurnNow(!isMaximizingTurnNow());
        }
        return  result;
    }

    void zliczpunkty(int osoba){
        int streak=0;  //ile jest obok siebie x/o
        int krok=0;
        int start_col = 0;      
        int start_wier = m-1;   
        int x=0;
        int y=0;
        wynik1=0;
        wynik2=0;

        // wszystko w prawo
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                 if (board[i][j] == E) {
                    continue;
                }

                streak = 0;
                krok = 0;

                // znlazlo jakis X
                 while(j+krok < n) {
                     if (board[i][j+krok] == osoba) {
                        streak++;
                        krok++;
                     }else{
                         j += krok;
                         break;
                     }
                 }
                if(osoba == X){wynik1 += streak*streak;}
                    else{wynik2 += streak*streak;}
            }
        }

        // do dolu
        for(int j=0; j<n; j++) {
            streak = 0;
            krok = 0;
            for (int i = 0; i < m; i++) {
                if (board[i][j] == E) {
                    continue;

                } else if (board[i][j] == osoba) {
                    streak++;
                }else{
                    if(osoba == X){wynik1 += streak*streak;}
                        else{wynik2 += streak*streak;}
                    streak = 0;
                }


            }
            if(osoba == X){wynik1 += streak*streak;}
            else{wynik2 += streak*streak;}
        }

        // wszystkie po skosie prawo i dol
        start_col = 0;      // numer kolumny
        start_wier = m-1;   // numer wiersza

        while(true){
            streak=0;
            x=0; y=0;

            // przeszukiwanie jednego skosu
            while ((start_col+x < n) && (start_wier+y < m)){

                if (board[start_wier + y][start_col + x] == E){
                    x++; y++;
                    if(osoba == X){wynik1 += streak*streak;}
                        else{wynik2 += streak*streak;}

                    streak = 0;

                }else if (board[start_wier + y][start_col + x] == osoba) {

                    streak++; x++; y++;

                } else {
                    if(osoba == X){wynik1 += streak*streak;}
                        else{wynik2 += streak*streak;}                    x ++;
                    y ++;
                    krok = 0;
                    streak = 0;
                }
            }
            if(osoba == X){wynik1 += streak*streak;}
                else{wynik2 += streak*streak;}

            if(start_wier!=0){start_wier--;}
                else{start_col++;}

            if(start_col>n)break;
        }

        start_col = n-1;   
        start_wier = m-1;                         

        while(true){
            streak=0;
            x=0; y=0;

            while ((start_col-x >= 0) && (start_wier+y < m)){

                if (board[start_wier + y][start_col - x] == E){
                    x++; y++;
                    if(osoba == X){wynik1 += streak*streak;}
                        else{wynik2 += streak*streak;}

                    streak = 0;

                }else if (board[start_wier + y][start_col - x] == X) {
                    streak++; x++; y++;

                } else {
                    if(osoba == X){wynik1 += streak*streak;}
                        else{wynik2 += streak*streak;}

                    x ++;
                    y ++;
                    krok = 0;
                    streak = 0;
                }
            }
            if(osoba == X){wynik1 += streak*streak;}
                else{wynik2 += streak*streak;}

            if(start_wier!=0){start_wier--;}
            else{start_col--;}

            if(start_col<0)break;
        }


        if(osoba == X){
            System.out.println("Twoj wynik:      " + wynik1);
        }
        else{
            System.out.println("Wynik komputera: " + wynik2);
        }


    }

    int sprawdz(){
        if(ileX==4){
            System.out.println("Bardzo sie starales i wygrales");
            zliczpunkty(X);
            zliczpunkty(O);
            System.exit(1);
            

        } else if(ileO==4) {
            System.out.println("Bardzo si? stara?e?, lecz z gry wyleciales.");
            zliczpunkty(X);
            zliczpunkty(O);
            System.exit(2);
        }
        ileX = 0;
        ileO = 0;
        return 0;
    }

    public double is_win() {
        int wygrana = 0;
        
        for(int i=0;i<n;i++){
        	if(board[0][i] == X){
        		ileX=4;
        		wygrana=sprawdz();
        		return Double.NEGATIVE_INFINITY;
        	}else if(board[0][i] == O){
        		ileO=4;
        		wygrana=sprawdz();
        		return Double.POSITIVE_INFINITY;
        	}
        }
            for (int i = m - 1; i >= 0; i--) {              
                for (int j = 0; j < n; j++) {         

                    if (board[i][j] == E) {
                        continue;
                    }

                    //jesli znalzlo x/o sprawdza w roznych kierunkach
                    if(j < n-3) {
                        for (int k = 0; k < 4; k++) {
                            if (board[i][j + k] == X) ileX++;
                            if (board[i][j + k] == O) ileO++;
                        }
                        wygrana = sprawdz();
                        if(wygrana==1) {
                            return Double.NEGATIVE_INFINITY;
                        }else if(wygrana==2){
                            return Double.POSITIVE_INFINITY;
                        }
                    }
                    if(i >= 3) {
                        for (int k = 0; k < 4; k++) {
                            if (board[i - k][j] == X) ileX++;
                            if (board[i - k][j] == O) ileO++;
                        }
                        
                        wygrana = sprawdz();
                        if(wygrana==1) {
                            return Double.NEGATIVE_INFINITY;
                        }else if(wygrana==2){
                            return Double.POSITIVE_INFINITY;
                        }                    }

                    if((j < n-3) && (i >= 3)) {
                        for (int k = 0; k < 4; k++) {
                            if (board[i - k][j + k] == X) ileX++;
                            if (board[i - k][j + k] == O) ileO++;
                        }
                        wygrana = sprawdz();
                        if(wygrana==1) {
                            return Double.NEGATIVE_INFINITY;
                        }else if(wygrana==2){
                            return Double.POSITIVE_INFINITY;
                        }                    }

                    if((j >= 3) && (i >= 3)) {
                        for (int k = 0; k < 4; k++) {
                            if (board[i - k][j - k] == X) ileX++;
                            if (board[i - k][j - k] == O) ileO++;
                        }
                        wygrana = sprawdz();
                        if(wygrana==1) {
                            return Double.NEGATIVE_INFINITY;
                        }else if(wygrana==2){
                            return Double.POSITIVE_INFINITY;
                        }                    }
                }
            }
        return 0;
    }


    public static void main(String[] args){

        scan = new Scanner(System.in);
        int wczytaj;
        boolean zacz=false;
        Connect.setHFunction(new Heurystyka());
        Connect plansza = new Connect();
        GameSearchConfigurator configurator = new GameSearchConfigurator();
        configurator.setDepthLimit(3.5);
        //GameSearchAlgorithm algorithm = new MinMax(plansza,configurator);

        GameSearchAlgorithm algorithm = new AlphaBetaPruning(plansza,configurator);

        
        System.out.println("Kto zaczyna? 1-Gracz | 2-Komputer");
        int kto = scan.nextInt();
        if(kto==1){
            plansza.setMaximizingTurnNow(false);
            zacz=false;
            
        }else{
            plansza.setMaximizingTurnNow(true);
            zacz=true;
           
        }

        System.out.println(plansza);
        while(true){
        	if(zacz){
        		System.out.println("Komputer mysli");
                algorithm.execute();
                plansza.make_move(Integer.parseInt(algorithm.getFirstBestMove()));
                System.out.println(plansza);
                System.out.println( "Punkty ruchow: " + algorithm . getMovesScores ());
                plansza.is_win();
                zacz=false;
        	}
        	else{
        		System.out.println("Podaj Kolumne: ");
        		wczytaj = scan.nextInt();
        		
        		while(wczytaj>n-1 || wczytaj <0){
        		System.out.println("Podaj ponownie Kolumne: ");
        		wczytaj = scan.nextInt();
        		}
        		         
                    plansza.make_move(wczytaj);
                    System.out.println(plansza);
                    plansza.is_win();
        		  
                
        		
        		zacz=true;
        	}
        	
        }
   }

}