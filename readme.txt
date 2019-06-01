Outputs:
    BFS [initial state = "1 34527896BCDAEF"]: (5, 204, 99, 106)
        [initial state = "123 56749AB8DEFC"]: (3, 17, 8, 10)

    DFS [initial state = "123456789ABC DEF"]: (3, 7, 4, 4)
        [initial state = " 12356749AB8DEFC"]: (6, 12, 7, 6)

    DLS [initial state = "1 34527896BCDAEF", depth limit = 5]: (5, 85, 80, 8)
        [initial state = "123 56749AB8DEFC", depth limit = 5]: (3, 7, 4, 4)

    GBFS [initial state = "1 3452689A7CDEBF", heuristic = h1]: (5, 15, 6, 10)
         [initial state = "12356749AB8 DEFC", heuristic = h1]: (289, 35326, 17508, 17819)

         [initial state = "1 3452689A7CDEBF", heuristic = h2]: (5, 15, 6, 10)
         [initial state = "12356749AB8 DEFC", heuristic = h2]: (53, 1073, 550, 524)

    A* [initial state = "1 3452689A7CDEBF", heuristic = h1]: (5, 14, 7, 9)
       [initial state = "1 3452689A7CDEBF", heuristic = h2]: (5, 14, 7, 9)

       [initial state = "123 56749AB8DEFC", heuristic = h1]: (3, 7, 5, 4)
       [initial state = "13452789 6BCDAEF", heuristic = h2]: (34, 321293, 167514, 153781)

Worst-case Time Complexities (given branching factor of 4):

    BFS, DFS, DLS* & GBFS: O(4^d); d = maximum depth of tree. *Max depth of tree is specified with DLS.
    
    A* w/ unoptimal heuristic: O(4^d)
    A* w/ perfectly optimal heuristic: O(log h(x)); h(x) = heuristic function.