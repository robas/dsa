package main

import "fmt"

type UnionFind struct {
	a     []int
	sz    []int
	count int
}

// Inits the QuickFind with n elements
func (uf *UnionFind) Init(n int) {
	uf.a = make([]int, n)
	uf.sz = make([]int, n)
	for i := 0; i < n; i++ {
		uf.a[i] = i
		uf.sz[i] = 1
	}
	uf.count = n
}

// Returns the canonical element of the set containing element p.
func (uf *UnionFind) Find(x int) int {
	var i int
	for i = uf.a[x]; i != uf.a[i]; i = uf.a[i] {
	}
	// fmt.Print(i)
	// fmt.Print(" ")
	// fmt.Print(uf.a[i])

	return i
}

// Merges the set containing element p with the the set containing element q.
func (uf *UnionFind) Union(x, y int) {
	rootX := uf.Find(x)
	rootY := uf.Find(y)

	if rootX == rootY {
		return
	}
	if uf.sz[rootX] >= uf.sz[rootY] {
		uf.a[rootX] = uf.a[rootY]
		uf.sz[rootY] += uf.sz[rootX]
	} else {
		uf.a[rootY] = uf.a[rootX]
		uf.sz[rootX] += uf.sz[rootY]
	}
	uf.count--
	return
}

// func (uf *UnionFind) Union(x, y int) {
// 	uf.a[x-1] = y - 1

// }

func (uf *UnionFind) Connected(x, y int) bool {
	return uf.Find(x) == uf.Find(y)
}

func main() {
	uf := UnionFind{}
	uf.Init(10)
	uf.Union(1, 0)
	uf.Union(2, 1)
	uf.Union(3, 2)

	fmt.Println(uf.a)
	fmt.Println(uf.Find(0))
	fmt.Println(uf.Find(1))
	fmt.Println(uf.Find(2))
	fmt.Println(uf.Find(3))
	fmt.Println(uf.Find(4))
	fmt.Println(uf.Find(5))
	fmt.Println(uf.Find(6))
	fmt.Println(uf.Connected(0, 3))
	fmt.Println(uf.Connected(0, 4))

	uf.Init(10)
	uf.Union(3, 4)
	uf.Union(8, 4)
	uf.Union(9, 4)
	uf.Union(0, 6)
	uf.Union(5, 6)
	uf.Union(4, 7)
	uf.Union(1, 2)
	uf.Union(7, 2)
	fmt.Println(uf.a)
	fmt.Println(uf.sz)

}
