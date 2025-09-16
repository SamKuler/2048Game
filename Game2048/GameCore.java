package Game2048;

import java.io.InputStream;
import java.util.*;

public class GameCore {
    private final int MAXSIZE = 8;
    private int score;
    private int maxNumber;
    private int moves;
    private int size;
    private int level;
    private int[][] board;
    private int spaceLeft;
    private Random rand = new Random();
    private boolean gameOver=false;
    private boolean gameWon=false;

    public enum DIRECTION{
        UP,DOWN,LEFT,RIGHT
    }
    public GameUI gameUI;

    public GameCore(int size,int level,int[][] initMatrix) {
        start(size,level,initMatrix);
    }
    public void start(int size,int level,int [][] initMatrix){
        if(size > MAXSIZE){
            System.out.printf("Error: Size > MAXSIZE which is %d. Set size to %d.%n", MAXSIZE, MAXSIZE);
            this.size = MAXSIZE;
        }
        else if (size < 2) {
            System.out.println("Error: Size is too small! Set size to default which is 4");
            this.size = 4;
        }
        else{
            this.size = size;
        }
        if(initMatrix==null)
            this.board = new int[this.size][this.size];
        else
            this.board = initMatrix.clone();
        this.level=level;
        gameOver=false;
        gameWon=false;
        spaceLeft = this.size*this.size;
        score = 0;
        maxNumber = 0;
        moves = 0;
        addRandom();
        addRandom();
    }
    public void printBoard(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }
    private void addRandom(){
        int pos=rand.nextInt(size*size);
        while(board[pos/size][pos%size] != 0){
            pos=rand.nextInt(size*size);
        }
        board[pos/size][pos%size]=rand.nextInt()%2==0?2:4;
        spaceLeft--;
    }
    public void move(DIRECTION direction)
    {
        if(gameOver)
        {
            System.out.println("Game Over!");
            return;
        }
        boolean moved = false;
        int scoreGained = 0;
        Pair<Integer,Boolean> res;
        switch(direction){
            case UP:
                res=moveUp();
                scoreGained=res.first;
                moved=res.second;
                break;
            case DOWN:
                res=moveDown();
                scoreGained=res.first;
                moved=res.second;
                break;
            case LEFT:
                res=moveLeft();
                scoreGained=res.first;
                moved=res.second;
                break;
            case RIGHT:
                res=moveRight();
                scoreGained=res.first;
                moved=res.second;
                break;
            default:
                break;
        }
        if(moved)
        {
            this.score+=scoreGained;
            this.moves++;
            addRandom();
            if(spaceLeft==0)
            {
                if(checkFailure())
                {
                    gameOver();
                }
            }
            if(!gameWon&&maxNumber==Math.pow(2,level))
            {
                System.out.println("Game Won!");
                gameWon=true;
                gameUI.gameWin();
            }
        }
    }
    private Pair<Integer, Boolean> moveLeft(){
        int scoreGained=0;
        boolean moved=false;
        for(int row = 0; row<size; row++){
            Deque<Integer> dq = new ArrayDeque<>();
            boolean rowMoved=false;
            for(int col=0;col<size;col++){
                if(board[row][col]==0){
                    continue;
                }
                int res=board[row][col];
                if(!dq.isEmpty()&&dq.peekLast()==res){
                    dq.addLast(dq.pollLast()+res);
                    spaceLeft++;
                    scoreGained+=2*res;
                    maxNumber=Math.max(maxNumber,2*res);
                }
                else{
                    dq.addLast(res);
                }
            }
            for(int col=0;col<size;col++){
                if(!dq.isEmpty()){
                    if(dq.peek()!=board[row][col]){
                        rowMoved=true;
                    }
                    board[row][col]=dq.poll();
                }
                else{
                    if(board[row][col]!=0){
                        rowMoved=true;
                    }
                    board[row][col]=0;
                }
            }
            if(rowMoved)
                moved=true;
        }
        return Pair.of(scoreGained,moved);
    }
    private Pair<Integer, Boolean> moveRight(){
        int scoreGained=0;
        boolean moved=false;
        for(int row = 0; row<size; row++){
            Deque<Integer> dq = new ArrayDeque<>();
            for(int col=size-1;col>=0;col--){
                if(board[row][col]==0){
                    continue;
                }
                int res=board[row][col];
                if(!dq.isEmpty()&&dq.peekLast()==res){
                    dq.addLast(dq.pollLast()+res);
                    spaceLeft++;
                    scoreGained+=2*res;
                    maxNumber=Math.max(maxNumber,2*res);
                }
                else{
                    dq.addLast(res);
                }
            }
            boolean rowMoved=false;
            for(int col=size-1;col>=0;col--){
                if(!dq.isEmpty()){
                    if(dq.peek()!=board[row][col]){
                        rowMoved=true;
                    }
                    board[row][col]=dq.poll();
                }
                else{
                    if(board[row][col]!=0){
                        rowMoved=true;
                    }
                    board[row][col]=0;
                }
            }
            if(rowMoved)
                moved=true;
        }
        return Pair.of(scoreGained,moved);
    }
    private Pair<Integer,Boolean> moveUp(){
        int scoreGained=0;
        boolean moved=false;
        for(int col=0;col<size;col++){
            Deque<Integer> dq = new ArrayDeque<>();
            for(int row=0;row<size;row++){
                if(board[row][col]==0){
                    continue;
                }
                int res=board[row][col];
                if(!dq.isEmpty()&&dq.peekLast()==res){
                    dq.addLast(dq.pollLast()+res);
                    spaceLeft++;
                    scoreGained+=2*res;
                    maxNumber=Math.max(maxNumber,2*res);
                }
                else{
                    dq.addLast(res);
                }
            }
            boolean colMoved=false;
            for(int row=0;row<size;row++){
                if(!dq.isEmpty()){
                    if(dq.peek()!=board[row][col]){
                        colMoved=true;
                    }
                    board[row][col]=dq.poll();
                }
                else{
                    if(board[row][col]!=0){
                        colMoved=true;
                    }
                    board[row][col]=0;
                }
            }
            if(colMoved)
                moved=true;
        }
        return Pair.of(scoreGained,moved);
    }
    private Pair<Integer, Boolean> moveDown(){
        int scoreGained=0;
        boolean moved=false;
        for(int col=0;col<size;col++){
            Deque<Integer> dq = new ArrayDeque<>();
            for(int row=size-1;row>=0;row--){
                if(board[row][col]==0){
                    continue;
                }
                int res=board[row][col];
                if(!dq.isEmpty()&&dq.peekLast()==res){
                    dq.addLast(dq.pollLast()+res);
                    spaceLeft++;
                    scoreGained+=2*res;
                    maxNumber=Math.max(maxNumber,2*res);
                }
                else{
                    dq.addLast(res);
                }
            }
            boolean colMoved=false;
            for(int row=size-1;row>=0;row--){
                if(!dq.isEmpty()){
                    if(dq.peek()!=board[row][col]){
                        colMoved=true;
                    }
                    board[row][col]=dq.poll();
                }
                else{
                    if(board[row][col]!=0){
                        colMoved=true;
                    }
                    board[row][col]=0;
                }
            }
            if(colMoved)
                moved=true;
        }
        return Pair.of(scoreGained,moved);
    }
    private boolean checkFailure(){
        int[] dx={0,0,1,-1};
        int[] dy={1,-1,0,0};
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                boolean continuation=false;
                for(int dirs=0;dirs<4;dirs++) {
                    int px=i+dx[dirs];
                    int py=j+dy[dirs];
                    if(px<0||px>=size||py<0||py>=size){
                        continue;
                    }
                    if (board[px][py] == board[i][j]) {
                        continuation = true;
                        break;
                    }
                }
                if(continuation){
                    return false;
                }
            }
        }
        return true;
    }
    public void gameOver(){
        gameOver=true;
        gameUI.gameOver();
        return;
    }
    public void cmdInteract(InputStream inputStream)
    {
        Scanner sc=new Scanner(inputStream);
        while(true) {
            if(sc.hasNext()) {
                char key=sc.next().charAt(0);
                switch(key) {
                    case 'w':
                        move(GameCore.DIRECTION.UP);
                        break;
                    case 's':
                        move(GameCore.DIRECTION.DOWN);
                        break;
                    case 'a':
                        move(GameCore.DIRECTION.LEFT);
                        break;
                    case 'd':
                        move(GameCore.DIRECTION.RIGHT);
                        break;
                    case 'q':
                        return;
                }
                printBoard();
            }
        }
    }
    public void debug(){
        System.out.println("Space Left"+spaceLeft);
        System.out.println("Score"+score);
        System.out.println("Moves"+moves);
    }
    public int getNumAt(int row,int col)
    {
        if(0<=row&&row<size&&0<=col&&col<size)
            return board[row][col];
        else
            return -1;
    }
    public int getSize()
    {
        return size;
    }
    public int getScore()
    {
        return score;
    }
    public int getMaxNumber()
    {
        return maxNumber;
    }
    public int getMoves()
    {
        return moves;
    }
    public int getLevel()
    {
        return level;
    }
    public boolean getWin()
    {
        return gameWon;
    }
}
