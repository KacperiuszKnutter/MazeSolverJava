import java.util.ArrayList;
import java.util.List;

public class Node {

    private int posX;
    private int posY;
    private boolean isEmpty;
    private Node leftNode, rightNode, upperNode, downNode;

    public Node(int x, int y) {
        this.posX = x;
        this.posY = y;
    }
    public void setEmpty(boolean isempty) {
        this.isEmpty = isempty;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }
    public int getXPos() {
        return posX;
    }

    public int getYPos() {
        return posY;
    }

    public void setX(int x) {
        this.posX = x;
    }

    public void setY(int y) {
        this.posY = y;
    }

    public boolean EndFound(MazeData mazeData) {
        return (mazeData.getMazeEndCol() == posX && mazeData.getMazeEndRow() == posY);
    }

    public boolean wasVisited(MazeData mazeData) {
        return mazeData.isGridVisitedMaze(posX, posY);
    }

    public void setDirections(Node left, Node up, Node right, Node down) {
        this.leftNode = left;
        this.rightNode = right;
        this.upperNode = up;
        this.downNode = down;
    }

    public List<Node> getAdjecentNodes(MazeData mazeData) {
        List<Node> neighbourNodes = new ArrayList<>();
        if (leftNode != null && mazeData.isPosAvailable(leftNode.getXPos(), leftNode.getYPos())) {
            neighbourNodes.add(leftNode);
        }
        if (downNode != null && mazeData.isPosAvailable(downNode.getXPos(), downNode.getYPos())) {
            neighbourNodes.add(downNode);
        }
        if (rightNode != null && mazeData.isPosAvailable(rightNode.getXPos(), rightNode.getYPos())) {
            neighbourNodes.add(rightNode);
        }
        if (upperNode != null && mazeData.isPosAvailable(upperNode.getXPos(), upperNode.getYPos())) {
            neighbourNodes.add(upperNode);
        }
        return neighbourNodes;
    }
}
