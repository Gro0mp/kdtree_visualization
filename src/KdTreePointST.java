import dsa.LinkedQueue;
import dsa.MaxPQ;
import dsa.Point2D;
import dsa.RectHV;
import stdlib.StdIn;
import stdlib.StdOut;

public class KdTreePointST<Value> implements PointST<Value> {
    Node root; // Creates a new node pointing to the root.
    int n; // Number of nodes in the tree.

    // Constructs an empty symbol table.
    public KdTreePointST() {
        root = null;
        n = 0;
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return n;
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        // Calls the private put() method with appropriate arguments to insert
        // the given point and value into the 2DTree. And increment the number
        // of nodes by one.
        root = put(root, p, value, new RectHV(Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY), true);
        n++;
    }

    // Returns the value associated with the given point in this symbol table, or null.
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return get(root, p, true);
    }

    // Returns true if this symbol table contains the given point, and false otherwise.
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return get(p) != null;
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        // Creates two linked queues, q1 for traversal and
        // q2 for storing points.
        LinkedQueue<Node> q1 = new LinkedQueue<>();
        LinkedQueue<Point2D> q2 = new LinkedQueue<>();
        q1.enqueue(root);
        // While q1 is not empty, dequeue the first item in
        // q1 and set it as node. And if it is not null, enqueue
        // it into q2, and enqueue the left and right point
        // of node.
        while (!q1.isEmpty()) {
            Node node = q1.dequeue();
            if (node == null) {
                continue;
            }
            q2.enqueue(node.p);
            q1.enqueue(node.lb);
            q1.enqueue(node.rt);
        }
        return q2;
    }

    // Returns all the points in this symbol table that are inside the given rectangle.
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> q = new LinkedQueue<>();
        range(root, rect, q);
        return q;
    }

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return nearest(root, p, root.p, true);
    }

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        MaxPQ<Point2D> pq = new MaxPQ<>(p.distanceToOrder());
        nearest(root, p, k, pq, true);
        return pq;
    }

    // Note: In the helper methods that have lr as a parameter, its value specifies how to
    // compare the point p with the point x.p. If true, the points are compared by their
    // x-coordinates; otherwise, the points are compared by their y-coordinates. If the
    // comparison of the coordinates (x or y) is true, the recursive call is made on x.lb;
    // otherwise, the call is made on x.rt.

    // Inserts the given point and value into the KdTree x having rect as its axis-aligned
    // rectangle, and returns a reference to the modified tree.
    private Node put(Node x, Point2D p, Value value, RectHV rect, boolean lr) {
        if (x == null) {
            return new Node(p, value, rect);
        }
        // If the point in x is the same as the given point, update
        // the value in x to the given value.
        if (x.p.equals(p)) {
            x.value = value;
        }
        // If lr is true, compare points by their x-coordinate.
        if (lr) {
            // If the x-coordinate of p is less than the x-coordinate of the
            // point in x, make a recursive to put() on the left subtree.
            if (p.x() < x.p.x()) {
                x.lb = put(x.lb, p, value, new RectHV(rect.xMin(), rect.yMin(),
                        x.p.x(), rect.yMax()), false);
                // If the x-coordinate of p is greater than or equal to the x-coordinate
                // of the point in x, make a recursive to put() on the right subtree.
            } else if (p.x() >= x.p.x()) {
                x.rt = put(x.rt, p, value, new RectHV(x.p.x(), rect.yMin(),
                        rect.xMax(), rect.yMax()), false);
            }
        } else {
            // If the y-coordinate of p is less than the y-coordinate of the
            // point in x, make a recursive to put() on the left subtree.
            if (p.y() < x.p.y()) {
                x.lb = put(x.lb, p, value, new RectHV(rect.xMin(), rect.yMin(),
                        rect.xMax(), x.p.y()), true);
                // If the y-coordinate of p is greater than or equal to the y-coordinate
                // of the point in x, make a recursive to put() on the right subtree.
            } else if (p.y() >= x.p.y()) {
                x.rt = put(x.rt, p, value, new RectHV(rect.xMin(), x.p.y(),
                        rect.xMax(), rect.yMax()), true);
            }
        }
        return x;
    }

    // Returns the value associated with the given point in the KdTree x, or null.
    private Value get(Node x, Point2D p, boolean lr) {
        if (x == null) {
            return null;
        }
        // If the point in x is the same as the given point, return
        // the value in x.
        if (x.p.equals(p)) {
            return x.value;
        }
        // If lr is true, compare points by their x-coordinate.
        if (lr) {
            // If the x-coordinate of p is greater than the x-coordinate
            // of the point in x, make a recursive to get() with the right
            // subtree as a parameter.
            if (p.x() > x.p.x()) {
                return get(x.rt, p, true);
            } else if (p.x() < x.p.x()) {
                // If the x-coordinate of p is less than the x-coordinate
                // of the point in x, make a recursive to get() with the left
                // subtree as a parameter.
                return get(x.lb, p, true);
            }
        } else {
            // If the y-coordinate of p is greater than the y-coordinate
            // of the point in x, make a recursive to get() with the right
            // subtree as a parameter.
            if (p.y() > x.p.y()) {
                return get(x.rt, p, false);
                // If the y-coordinate of p is less than the y-coordinate
                // of the point in x, make a recursive to get() with the left
                // subtree as a parameter.
            } else if (p.y() < x.p.y()) {
                return get(x.lb, p, false);
            }
        }
        return x.value;
    }

    // Collects in the given queue all the points in the KdTree x that are inside rect.
    private void range(Node x, RectHV rect, LinkedQueue<Point2D> q) {
        if (x == null) {
            return;
        }
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        // If rect contains the point in x, enqueue the point into q.
        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }
        // Recursively call range() on the left and right subtree of x using
        // the pruning method.
        if (rect.intersects(root.rect)) {
            range(x.lb, rect, q);
            range(x.rt, rect, q);
        }
    }

    // Returns the point in the KdTree x that is closest to p, or null; nearest is the closest
    // point discovered so far.
    private Point2D nearest(Node x, Point2D p, Point2D nearest, boolean lr) {
        Point2D near = nearest;
        if (x == null) {
            return near;
        }
        // If the point x.p is different from the given point p and the squared
        // distance between the two is smaller than the squared distance between
        // nearest and p, update near to x.p.
        if (x.p != p && x.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            near = x.p;
        }
        // Make a recursive call on the left subtree of x.lb, comparing
        // by their x-coordinate.
        if (lr) {
            near = nearest(x.lb, p, near, true);
            // Based on the value returned by the previous recursive
            // call on the left subtree, make a recursive call on the
            // right subtree.
            if (near.distanceSquaredTo(p) > x.rect.distanceSquaredTo(p)) {
                near = nearest(x.rt, p, near, true);
            }
        } else {
            // Make a recursive call on the left subtree of x.lb, comparing
            // by their x-coordinate.
            near = nearest(x.rt, p, near, false);
            // Based on the value returned by the previous recursive
            // call on the left subtree, make a recursive call on the
            // right subtree.
            if (near.distanceSquaredTo(p) > x.rect.distanceSquaredTo(p)) {
                near = nearest(x.lb, p, near, false);
            }
        }
        return near;
    }

    // Collects in the given max-PQ up to k points from the KdTree x that are different from and
    // closest to p.
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, boolean lr) {
        if (x == null || pq.size() > k && pq.max().distanceSquaredTo(p)
                < x.rect.distanceSquaredTo(p)) {
            return;
        }
        // If the point in x is different from the given point, insert
        // it into pq.
        if (!x.p.equals(p)) {
            pq.insert(x.p);
        }
        // If the size of pq exceeds k, remove the maximum point from
        // the pq.
        if (pq.size() > k) {
            pq.delMax();
        }
        // If lr is true, compare points by their x-coordinate. Else,
        // compare them by their y-coordinates.
        if (lr) {
            // If the x-coordinate of p is greater than the x-coordinate
            // of the point in x, make a recursive to nearest() twice.
            // First with the right subtree as a parameter, then the left
            // subtree.
            if (p.x() > x.p.x()) {
                nearest(x.rt, p, k, pq, true);
                nearest(x.lb, p, k, pq, true);
            } else {
                // If the x-coordinate of p is less than the x-coordinate
                // of the point in x, make a recursive to nearest() twice.
                // First with the left subtree as a parameter, then the right
                // subtree.
                nearest(x.lb, p, k, pq, true);
                nearest(x.rt, p, k, pq, true);
            }
        } else {
            // If the y-coordinate of p is greater than the y-coordinate
            // of the point in x, make a recursive to nearest() twice.
            // First with the right subtree as a parameter, then the left
            // subtree.
            if (p.y() > x.p.y()) {
                nearest(x.rt, p, k, pq, false);
                nearest(x.lb, p, k, pq, false);
            } else {
                // If the y-coordinate of p is less than the y-coordinate
                // of the point in x, make a recursive to nearest() twice.
                // First with the left subtree as a parameter, then the right
                // subtree.
                nearest(x.lb, p, k, pq, false);
                nearest(x.rt, p, k, pq, false);
            }
        }
    }

    // A representation of node in a KdTree in two dimensions (ie, a 2dTree). Each node stores a
    // 2d point (the key), a value, an axis-aligned rectangle, and references to the left/bottom
    // and right/top subtrees.
    private class Node {
        private Point2D p;   // the point (key)
        private Value value; // the value
        private RectHV rect; // the axis-aligned rectangle
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Constructs a node given the point (key), the associated value, and the
        // corresponding axis-aligned rectangle.
        Node(Point2D p, Value value, RectHV rect) {
            this.p = p;
            this.value = value;
            this.rect = rect;
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
