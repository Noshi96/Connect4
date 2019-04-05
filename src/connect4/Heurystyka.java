package connect4;


import sac.State;
import sac.StateFunction;

public class Heurystyka extends StateFunction {


    @Override
    public double calculate(State state) {


        int h = 0;
        byte [][] board = ((Connect)state).getBoard();

        int m = Connect.m;
        int n = Connect.n;

        byte X = Connect.X;
        byte O = Connect.O;
        byte E = Connect.E;

        int powtorzenia=0;
        int k=0;
        int pierwsza_kolumna = 0;
        int poczatkowy_wiersz = m-1;
        int x=0;
        int y=0;
int pion=0;
        boolean brak_ciagu=false;

        if(((Connect) state).isMaximizingTurnNow()) {

            // regula sufitu
            for(int i=0; i<n; i++){
                if(board[0][i] == O)
                    return Double.POSITIVE_INFINITY;
            }


            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == E) {
                        continue;
                    }
                    	//poszukiwanie O
                    powtorzenia = 0;
                    k = 0;


                    while (j + k < n) {
                        if (board[i][j + k] == O) {
                            powtorzenia++;
                            k++;


                            if (powtorzenia == 4) return Double.POSITIVE_INFINITY;
                        } else {
                            j += k;
                            break;
                        }
                    }
                    if(powtorzenia!=0) {
                        h+=h*powtorzenia+1;

                    }
                }
            }
            // do dolu
            for(int j=0; j<n; j++) {
                powtorzenia = 0;
                k = 0;
                for (int i = 0; i < m; i++) {
                    if (board[i][j] == E) {
                        continue;

                    } else if (board[i][j] == O) {
                        powtorzenia++;
                        if(powtorzenia==3) pion=h*powtorzenia;
                        if (powtorzenia == 4) return Double.POSITIVE_INFINITY;
                    }else{
                        if(powtorzenia!=0)
                            h+=h*powtorzenia+1;
                        powtorzenia = 0;
                    }


                }
                if(powtorzenia!=0)
                    h+=h*powtorzenia+1*pion;
                //pion=0;
            }

            // wszystkie po skosie prawo i dol
            pierwsza_kolumna = 0;      // numer kolumny
            poczatkowy_wiersz = m - 1;   // numer wiersza

            while (true) {
                powtorzenia = 0;
                x = 0;
                y = 0;

                // przeszukiwanie jednego skosu
                while ((pierwsza_kolumna + x < n) && (poczatkowy_wiersz + y < m)) {

                    if (board[poczatkowy_wiersz + y][pierwsza_kolumna + x] == E) {
                        x++;
                        y++;
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;

                        powtorzenia = 0;

                    } else if (board[poczatkowy_wiersz + y][pierwsza_kolumna + x] == O) {

                        powtorzenia++;
                        x++;
                        y++;
                        if (powtorzenia == 4) return Double.POSITIVE_INFINITY;

                    } else {
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;
                        x++;
                        y++;
                        k = 0;
                        powtorzenia = 0;
                    }
                }
                h+=h*powtorzenia+1;

                if (poczatkowy_wiersz != 0) {
                    poczatkowy_wiersz--;
                } else {
                    pierwsza_kolumna++;
                }

                if (pierwsza_kolumna > n) break;
            }


            // wszystkie po skosie lewo i dol

            pierwsza_kolumna = n - 1;
            poczatkowy_wiersz = m - 1;

            while (true) {
                //System.out.println("#1.");
                powtorzenia = 0;
                x = 0;
                y = 0;

                // przeszukiwanie jednego skosu
                while ((pierwsza_kolumna - x >= 0) && (poczatkowy_wiersz + y < m)) {

                    if (board[poczatkowy_wiersz + y][pierwsza_kolumna - x] == E) {
                        x++;
                        y++;
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;

                        powtorzenia = 0;

                    } else if (board[poczatkowy_wiersz + y][pierwsza_kolumna - x] == O) {
                        powtorzenia++;
                        x++;
                        y++;
                        if (powtorzenia == 4) return Double.POSITIVE_INFINITY;

                    } else {
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;

                        x++;
                        y++;
                        k = 0;
                        powtorzenia = 0;
                    }
                }
                h+=h*powtorzenia+1;

                if (poczatkowy_wiersz != 0) {
                    poczatkowy_wiersz--;
                } else {
                    pierwsza_kolumna--;
                }

                if (pierwsza_kolumna < 0) break;
            }


        }
        else{

            // regula sufitu
            for(int i=0; i<n; i++){
                if(board[0][i]== X)
                    return Double.NEGATIVE_INFINITY;
            }

            // wszystko w prawo
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == E) {
                        continue;
                    }

                    powtorzenia = 0;
                    k = 0;

                    // znlazlo jakis O
                    while (j + k < n) {
                        if (board[i][j + k] == X) {
                            powtorzenia++;
                            k++;
                            if (powtorzenia == 4) return Double.NEGATIVE_INFINITY;
                        } else {
                            j += k;
                            break;
                        }
                    }

                    h+=h*powtorzenia+1;

                }
            }


            // wszystko w dol
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    if (board[i][j] == E) {
                        continue;
                    }

                    powtorzenia = 0;
                    k = 0;

                    // znlazlo jakis O
                    while (i + k < m) {
                        if (board[i + k][j] == X) {
                            powtorzenia++;
                            k++;
                            if(powtorzenia==3) pion=h*powtorzenia;
                            if (powtorzenia == 4) return Double.NEGATIVE_INFINITY;
                        } else {
                            i += k;
                            break;
                        }
                    }
                    h+=h*powtorzenia+1*pion;
                   // pion=0;
                }
            }


            // wszystkie po skosie prawo i dol
            pierwsza_kolumna = 0;      // numer kolumny
            poczatkowy_wiersz = m - 1;   // numer wiersza

            while (true) {
                powtorzenia = 0;
                x = 0;
                y = 0;

                // przeszukiwanie jednego skosu
                while ((pierwsza_kolumna + x < n) && (poczatkowy_wiersz + y < m)) {

                    if (board[poczatkowy_wiersz + y][pierwsza_kolumna + x] == E) {
                        x++;
                        y++;
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;

                        powtorzenia = 0;

                    } else if (board[poczatkowy_wiersz + y][pierwsza_kolumna + x] == X) {

                        powtorzenia++;
                        x++;
                        y++;
                        if (powtorzenia == 4) return Double.NEGATIVE_INFINITY;

                    } else {
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;
                        x++;
                        y++;
                        k = 0;
                        powtorzenia = 0;
                    }
                }
                h+=h*powtorzenia+1;

                if (poczatkowy_wiersz != 0) {
                    poczatkowy_wiersz--;
                } else {
                    pierwsza_kolumna++;
                }

                if (pierwsza_kolumna > n) break;
            }


            // wszystkie po skosie lewo i dol

            pierwsza_kolumna = n - 1;
            poczatkowy_wiersz = m - 1;

            while (true) {
                powtorzenia = 0;
                x = 0;
                y = 0;

                // przeszukiwanie jednego skosu
                while ((pierwsza_kolumna - x >= 0) && (poczatkowy_wiersz + y < m)) {

                    if (board[poczatkowy_wiersz + y][pierwsza_kolumna - x] == E) {
                        x++;
                        y++;
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;

                        powtorzenia = 0;

                    } else if (board[poczatkowy_wiersz + y][pierwsza_kolumna - x] == X) {
                        powtorzenia++;
                        x++;
                        y++;
                        if (powtorzenia == 4) return Double.NEGATIVE_INFINITY;

                    } else {
                        if(powtorzenia!=0)
                        h+=h*powtorzenia+1;

                        x++;
                        y++;
                        k = 0;
                        powtorzenia = 0;
                    }
                }
                if(powtorzenia!=0)
                h+=h*powtorzenia+1;

                if (poczatkowy_wiersz != 0) {
                    poczatkowy_wiersz--;
                } else {
                    pierwsza_kolumna--;
                }

                if (pierwsza_kolumna < 0) break;
            }




        }


        return h;
    }
}


/*

import sac.State;
import sac.StateFunction;

public class Heurystyka extends StateFunction {


    @Override
    public double calculate(State state) {
        int h = 0;
        int m = Connect.m;
        int n = Connect.n;
        byte X = X;
        byte O = O;
        byte E = E;

        byte[][] board = ((Connect) state).getBoard();
        int suma = 0;
        int ile0 = 0;
        int ile1 = 0;
        int ile2 = 0;
        int ile3 = 0;
        int ile4 = 0;
        int suma_pion = 0, suma_poziom = 0, suma_skos_gora = 0, suma_skos_dol = 0;
        int calosc = 0;

        int licz_poziom_poziom = 0;
        int licz_poziom_pion = 0;
        int licz_poziom_skos_gora = 0;
        int licz_poziom_skos_dol = 0;
        boolean wystapilo1 = false;
        boolean wystapilo2 = false;
        boolean wystapilo3 = false;
        boolean wystapilo4 = false;

        if (((Connect) state).isMaximizingTurnNow()) {
            // regula sufitu
            for (int i = 0; i < n; i++) {
                if (board[0][i] == O)
                    return Double.POSITIVE_INFINITY;
            }


            for (int i = m - 1; i >= 0; i--) {
                for (int j = 0; j < n; j++) {


                    for (int k = 0; k < 4; k++) {
                        if (i - k >= 0 && j + k < n) {


                            if (board[i][j + k] == X && wystapilo1 == false) {//sprawdzanie wiersza
                                licz_poziom_poziom += 1;
                            }
                            if (licz_poziom_poziom != 0) {
                                if (board[i][j + k] == O)
                                    wystapilo1 = true;
                            }


                            if (board[i - k][k + j] == X && wystapilo2 == false) {//skos do gory
                                licz_poziom_skos_gora += 1;
                            }
                            if (licz_poziom_skos_gora != 0) {
                                if (board[i - k][k + j] == O)//skos do gory
                                    wystapilo2 = true;
                            }

                            if (board[i - k][j] == X && wystapilo3 == false) {//pion kolumny
                                licz_poziom_pion += 1;
                            }
                            if (licz_poziom_pion != 0) {
                                if (board[i - k][j] == O)//pio
                                    wystapilo3 = true;
                            }


                            if(m-i+k<m) {
                                if (board[m - i + k][j + k] == X && wystapilo4 == false) {//
                                    licz_poziom_skos_dol += 1;
                                }
                                if (licz_poziom_skos_dol != 0) {
                                    if (board[m - i + k][j + k] == O)//skos w dol
                                        wystapilo4 = true;
                                }
                            }

                        }
                    }
                    wystapilo1 = false;
                    wystapilo2 = false;
                    wystapilo3 = false;
                    wystapilo4 = false;

                    if (licz_poziom_poziom == 1) {
                        ile1 += 1;
                       // suma_poziom = 20;
                       // calosc = calosc + suma_poziom;
                        licz_poziom_poziom = 0;
                    } else if (licz_poziom_poziom == 2) {
                        //System.out.println("1");
                        ile2 += 1;
                        // if(suma_poziom<200) {
                        suma_poziom = 200;
                        calosc = calosc + suma_poziom;
                        // }
                        licz_poziom_poziom = 0;
                    } else if (licz_poziom_poziom == 3) {
                        //System.out.println("2");
                        ile3 += 1;
                        suma_poziom = 500;
                        calosc = calosc + suma_poziom;
                        licz_poziom_poziom = 0;
                    } else if (licz_poziom_poziom == 4) {
                        //System.out.println("3");
                        ile4 += 1;
                        suma_poziom = 1100;
                        calosc = calosc + suma_poziom;
                        licz_poziom_poziom = 0;
                        return Double.POSITIVE_INFINITY; //break;
                    }
//////////////////////////////////////////////////////////////////////////
                    if (licz_poziom_skos_gora == 1) {
                        ile1 += 1;
                       // suma_skos_gora = 20;
                        licz_poziom_skos_gora = 0;
                    } else if (licz_poziom_skos_gora == 2) {
                        //System.out.println("4");
                        ile2 += 1;
                        // if(suma_skos_gora<200) {
                        suma_skos_gora = 190;
                        calosc = calosc + suma_skos_gora;
                        // }
                        licz_poziom_skos_gora = 0;
                    } else if (licz_poziom_skos_gora == 3) {
                        //System.out.println("5");
                        ile3 += 1;
                        suma_skos_gora = 350;
                        calosc = calosc + suma_skos_gora;
                        licz_poziom_skos_gora = 0;
                    } else if (licz_poziom_skos_gora == 4) {
                        //System.out.println("6");
                        ile4 += 1;
                        suma_skos_gora = 1200;
                        calosc = calosc + suma_skos_gora;
                        licz_poziom_skos_gora = 0;
                        return Double.POSITIVE_INFINITY;
                    }
////////////////////////////////////////////////////////////////////////
                    if (licz_poziom_pion == 1) {
                        ile1 += 1;
                        licz_poziom_pion = 0;
                    } else if (licz_poziom_pion == 2) {
                        //System.out.println("7");
                        ile2 += 1;
                        //if(suma_pion<200) {
                        suma_pion = 200;
                        calosc = calosc + suma_pion;
                        // }
                        licz_poziom_pion = 0;
                    } else if (licz_poziom_pion == 3) {
                        //System.out.println("8");
                        //System.out.println("W pionie 3");
                        ile3 += 1;
                        suma_pion = 360;
                        calosc = calosc + suma_pion;
                        licz_poziom_pion = 0;
                    } else if (licz_poziom_pion == 4) {
                        //System.out.println("9");
                        ile4 += 1;
                        suma_pion = 1300;
                        calosc = calosc + suma_pion;
                        licz_poziom_pion = 0;
                        return Double.POSITIVE_INFINITY;
                    }
///////////////////////////////////////////////////////////////////////

                    if (licz_poziom_skos_dol == 1) {
                        ile1 += 1;
                        licz_poziom_skos_dol = 0;
                    } else if (licz_poziom_skos_dol == 2) {
                        //System.out.println("10");
                        ile2 += 1;
                        //if(suma_skos_dol<200) {
                        suma_skos_dol = 200;
                        calosc = calosc + suma_skos_dol;
                        //}
                        licz_poziom_skos_dol = 0;
                    } else if (licz_poziom_skos_dol == 3) {
                        //System.out.println("11");
                        ile3 += 1;
                        suma_skos_dol = 380;
                        calosc = calosc + suma_skos_dol;
                        licz_poziom_skos_dol = 0;
                    } else if (licz_poziom_skos_dol == 4) {
                        //System.out.println("12");
                        ile4 += 1;
                        suma_skos_dol = 1400;
                        calosc = calosc + suma_skos_dol;
                        licz_poziom_skos_dol = 0;
                        return Double.POSITIVE_INFINITY;


                    }
                }


            }
        }
        return calosc;
    }
}

*/