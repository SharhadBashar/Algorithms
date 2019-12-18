#include <iostream>
using namespace std;
#include <queue>
#include <limits.h>

int inf = INT_MAX;
int best = inf;

void path(int **M, int n , int t , int weight, int cV, int&best, int* visited) {
	if (visited[cV] == 0) {
  	if (cV == t) {
  		if (weight < best) {
  			best = weight;
  		}
  		return;
  	}


  	for (int j =0; j < n; j++) {
  		if (j != cV) {
    		if (M[cV][j] < inf) {
    			if (weight + M[cV][j] > best) {}
    			else {
    				visited[cV] = 1;
    				path(M, n, t, weight + M[cV][j] , j, best, visited);
    				visited[cV] = 0;
    			}
    		}
      }
    }
  }
}

int main() {
	int n;
	int s;
	int t;
	cin >> n >> s >> t;

	int **M = new int*[n];

	int *visited = new int[n];

	for (int i = 0; i < n; i++) {
		visited[i] = 0;
	}

	int best = inf;

	for (int i = 0; i < n; i++) {
		M[i] = new int[n];
		for (int j = 0; j < n; j++) {
			M[i][j] = inf;
		}
	}

	for (int i = 0; i < n; i++) {
		int pairs;
		cin >> pairs;
		for (int j = 0; j < pairs; j++) {
			int Vj, Wj;
			cin >> Vj >> Wj;
			M[i][Vj - 1] = Wj;
		}

	}

	M[s - 1][s - 1] = 0;

	path(M, n, t - 1, 0, s - 1, best, visited);
	if (best == inf) {
		cout << "No path";
	}
	else {
	cout << best;
}
	return best;
}