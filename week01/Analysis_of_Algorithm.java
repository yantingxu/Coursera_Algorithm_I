/*
 * 1.4 Analysis of Algorithm
 * introduction
 * observations
 * mathematical models
 * order of growth classifications
 * theory of algorithms
 * memory
 */

// Reasons to Analysis: predict performance, compare algorithms, provide guarantees, understand theoretical basis
// Challenge: running time and memory for a large input N

// Scientific Method to Analysis
// 1. Hypothesize a model consistent with obs
// 2. Predict
// 3. Verify
// 4. Validate by iterating
// Principles: reproducible and falsifiable


// Observations (3-SUM brute-force)
// measure running time for different N
// standard plot; log-log plot; linear regression; make predictions and verify with observations
// doubling hypothesis for power-law relationship: T(N) = a*N^b
// 1. b = log(ratio) where ratio = T(2N)/T(N);
// 2. observe large N and then solve a with the known b

// System-independent effects: algorithm + input data => determines exponent b in power law
// System-depedent effects: hardware, software, os => determines the constant a in power law


// Mathematical models
// total running time: sum of cost * frequency for all ops
// 1. cost: system-dependent; for each operation, there is a cost c_i
// 2. frequency: system-independent, algorithm + input data
// Examples: 1-sum, 2-sum frequency of each operation; something variable w.r.t. input data

// Simplication 1: Use some base operation as a proxy for running time, e.g. most costly or most frquency; array access
// Simplication 2: tilde notation, ignoring lower order terms
// Example: 2-sum ~N^2 array accesses; 3-sum ~1/2*N^3 array accesses
// That is use cost model and tilde notation to simplify counts
// Hint: use intergral to compute discrete sum

// In summary, mathematical model for running time is the approximate model
// T(N) = c1*A + c2*B + c3*C, where ci is the cost of each op (system dependent) and A/B/C is the frequency (system independent);


// Order-of-growth classification
// Common functions: 1, logN, N, NlogN, N^2, N^3, 2^N (log-log plot)
// Example: Binary search tree with key comparison operation as proxy; T(N) <= T(N/2) + 1 with T(1) = 1 => T(N) = 1 + logN
// Example: N^2logN for 3-sum (sort, and then binary search for -(a[i]+a[j]) with the restriction a[i] <= a[j] <= a[k]); compare with brute-force 3-sum with the empirical data


// Theory of algorithms
// bast case; worst case; average case; actual data may not fit input model: for worst case; add random explicitly
// Goals
// 1. difficulty of the problem: "within a constant factor" + focus on worst case
// 2. optimal algorithm: performance gurantee (within a constant factor) for each input; no better gurantee
// O(n), \Theta(n), \Omega(n), ~n
// Example 1: 1-sum; upper bound (one specific alogrithm), lower bound (optimal gurantee), optimal algorithm (bounds meet)
// Example 2: 3-sum; upper bound (N^2logN), lower bound (\Omega(n)), optimal? still open problems
// Algorithm Design Approach: one speific algorithm => upper bound, prove a lower bound, reduce the gap by discovering a new algorithm


// Memory
// bit, byte, M, G
// 64-bit machine => 8-byte pointer
// for primitive types and array
// overhead: object 16bytes; reference 8bytes; padding: 8*M; array 24bytes
// shallow/deep memory useage


// TRACK SUMMARY
// 1. Empirical: time measurement, formulate model, make predictions
// 2. Math model: frequncy + tilde notation, explain behavior
// 3. Scientific method: Math model is independent of system, so need empirical data to validate and make predictions
