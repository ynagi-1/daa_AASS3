# Optimization of a City Transportation Network
## Summary of Input Data and Algorithm Results
I analyzed 15 different graphs of increasing size and density. Each graph was processed by both Prim’s and Kruskal’s algorithms. The table below summarizes the results, including total MST cost, execution time (in milliseconds), and operation count.

# Graph ID	Vertices	Edges	Prim Cost	Prim Time (ms)	Prim Ops	Kruskal Cost	Kruskal Time (ms)	Kruskal Ops
1	4	3	6	0.0107	3	6	0.0168	5
2	5	4	14	0.0228	4	14	0.0163	5
3	5	4	14	0.0127	4	14	0.0107	6
4	4	3	45	0.0086	3	45	0.0251	4
5	5	4	4	0.0105	4	4	0.0090	5
6	10	9	42	0.0194	10	42	0.0195	10
7	11	10	40	0.0242	10	40	0.0167	10
8	9	8	41	0.0177	8	41	0.0227	9
9	10	9	51	0.0179	9	51	0.0142	9
10	15	14	74	0.0283	14	74	0.0238	14
11	20	19	75	0.0788	22	75	0.0478	29
12	22	21	88	0.0947	23	88	0.0457	28
13	25	24	105	0.0832	27	105	0.0998	32
14	20	19	76	0.0794	19	76	0.0493	28
15	30	29	128	0.1052	29	128	0.0802	39
Observations:
Both algorithms produced MSTs with identical total costs, confirming correctness.

Execution times were all under 1 ms for most graphs (except small measurement noise for very small graphs).

Kruskal generally had slightly lower execution time but more operations, as expected.

Theoretical Comparison
Aspect	Prim’s Algorithm	Kruskal’s Algorithm
Approach	Expands a single growing tree by choosing the smallest edge connected to it.	Builds the MST by sorting edges and adding the smallest non-cyclic edge.
Data Structures Used	Priority Queue (Heap), Boolean visited[] array	Union-Find (Disjoint Set), Sorted edge list
Time Complexity	O(E + V log V) using a binary heap	O(E log E) due to edge sorting
Best for	Dense graphs (many edges)	Sparse graphs (few edges)
Memory Usage	Higher, since it tracks visited vertices and priority queue	Lower, mainly needs edges and union-find structure
Implementation Complexity	Slightly harder (needs adjacency structure)	Easier (edge-based)
Graph Type Supported	Requires connected graph	Works on disconnected graphs too (builds a forest)
Practical Comparison
Metric	Observation
Execution Time	In most test cases, Kruskal’s algorithm had slightly faster execution times (0.01–0.09 ms range) compared to Prim’s algorithm (0.02–0.10 ms). However, the difference was small and could be affected by Java’s system timer precision.
Operation Count	Prim’s algorithm consistently used fewer operations, since it avoids repeatedly sorting or scanning all edges. Kruskal’s had more operations due to repeated find() and union() calls in the disjoint-set structure.
Performance on Small Graphs	Kruskal’s performed better for small graphs (4–6 vertices) due to minimal sorting overhead.
Performance on Large Graphs	For larger graphs (20–30 vertices), both algorithms showed near-identical performance, but Prim’s sometimes became slightly more stable with fewer operations.
Scalability	Both algorithms scale well for moderate graph sizes. Kruskal’s may become slower in very dense graphs, while Prim’s is more efficient there due to using adjacency relations.
Conclusions
Based on both theory and experimental results:

Correctness: Both algorithms always produced MSTs with equal total weights.

Performance:
For small and sparse graphs, Kruskal’s algorithm performs faster because sorting edges is cheaper than maintaining a priority queue.

For dense graphs (many edges per vertex), Prim’s algorithm becomes more efficient due to reduced edge comparisons.

Implementation:
Kruskal’s algorithm is easier to implement and debug.

Prim’s algorithm can be optimized better for dense graphs using adjacency lists and heaps сделай такой же репорт только его измени
