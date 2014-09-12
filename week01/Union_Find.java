/*
 * 1.5 Union-Find
 * dynamic connectivity
 * quick find
 * quick union
 * improvements
 * applications
 */

// Develop a usable algorithm: model the problem -> find an algorithm -> check speed and memory -> if not, figure out why -> refine and iterate
// Motivating Example of Union-find: dynamic connectivity (给定N个独立对象, 可支持两个操作; Union: 用一条边连结两个结点, Find: 两个结点之间是否存在一条路径)
// 可以抽象为UF结构
// 1. 对对象建模: 直接用下标0-(N-1)表示对象;
// 2. 对连接建模: 无向连接(对称, 自反, 传递), 不需要记录具体谁与谁连接, 抽象为connected component即可(任意两个object之间都有path相连);
// 3. 实现操作: Union将两个compoent替换为它们的并集; Find两个对象是否在同一个component之中
// 需要有效的数据结构以实现以上操作: N和M都可能很大, 两种操作可以是交替的;

// Quick-find实现
class Quick_Find
{
    private int[] ids;      // idx => object, val => the representive object id of the corresponding component
    public Quick_Find(int n) {      // O(n)
        ids = new int[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
    }

    public boolean connected(int p, int q) {        // O(1)
        return ids[p] == ids[q];
    }

    public void union(int p, int q) {       // O(n), too costly to maintain the flat structure
        int pid = ids[p];
        int qid = ids[q];
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == pid)
                ids[i] = qid;
        }
    }
}

class Quick_Union
{
    private int[] ids;      // idx => object id; val => parent object id
    public Quick_Union(int n) {
        ids = new int[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
    }

    private int root(int p) {       // worst case: O(n), tree can be tall
        while (p != ids[p])
            p = ids[p];
        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int pid = root(p);
        int qid = root(q);
        ids[pid] = qid;
    }
}

class Weighted_Quick_Union
{
    private int[] ids;      // idx => object id; val => parent object id
    private int[] sizes;    // #node rooted in idx
    public Quick_Union(int n) {
        ids = new int[n];
        sizes = new int[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
            sizes[i] = 1;
        }
    }

    private int root(int p) {       // improvement 1 make the depth be at most logN (proof); so union and connected are both at most O(logN)
        while (p != ids[p]) {
            ids[p] = ids[ids[p]];   // improvement 2: make each node in the path point to the gradparent
            p = ids[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int pid = root(p);
        int qid = root(q);
        if (pid == qid)
            return;
        // improvement 1: link small tree to large tree to make the union tree not so tall;
        if (sizes[pid] < sizes[qid]) {
            ids[pid] = qid;
            sizes[qid] += sizes[pid];
        } else {
            ids[qid] = pid;
            sizes[pid] += sizes[qid];
        }
    }
}

// quick-union with any improvements in amortized analysis: N + M*logN
// quick-union with two improvements in amortized analysis: N + M*\alpha(M, N), almost linear

// another application of union-find: percolation (see PA1)

