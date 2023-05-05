# COMP3100DS Week 9 Task
Week 9
Baseline Algorithms w/out use of global queue 

The First Fit, Best Fit, and Worst Fit algorithms are all commonly used memory allocation algorithms in computer systems. Here are some of the pros and cons of each algorithm:

First Fit Algorithm:
Pros:
-	Simple and easy to implement.
-	Allocates memory quickly, as it searches for the first free block of memory that is big enough to satisfy the requested size.
-	Does not require sorting the memory blocks, which can save processing time.
Cons:
-	Can lead to memory fragmentation, as it may allocate larger blocks than necessary for small requests.
-	May not always find the best fit for the request, resulting in more wasted memory.

Best Fit Algorithm:
Pros:
-	Results in less memory fragmentation than the first fit algorithm, as it searches for the smallest free block of memory that is big enough to satisfy the requested size.
-	Generally allocates memory more efficiently, resulting in less wasted memory overall.
Cons:
-	More complex than the first fit algorithm, as it requires sorting the memory blocks based on size.
-	May be slower than the first fit algorithm, as it needs to search through the sorted list of memory blocks to find the best fit.

Worst Fit Algorithm:
Pros:
-	Results in the largest free memory block remaining after the allocation, which can be useful for larger requests in the future.
-	Allocates memory quickly, as it searches for the largest free block of memory.

Cons:
-	Can result in significant memory fragmentation, as it may allocate smaller blocks of memory in the remaining spaces.
-	Can lead to wasted memory, as it may not allocate the best fit for the request.

