(deftemplate state-info
    (slot row)
    (slot col)
    (slot group)
    (slot diag)
    (slot id)
    (slot value (default 0))
    (multislot unused-values)
    (multislot used-values)
)

(deftemplate maked
    (slot id)
    (slot value)
)

; **********************
; initiating state zero
; **********************

(defrule make-new-state
    (declare (salience 10))
    (phase init-forward)
    
    (possible (row ?r) (column ?c) (group ?g) (diag ?d) (id ?x) (value ?v))
    (not (maked (id ?x) (value ?v)))
    (not (state-info (id ?x)))
    =>
    (assert (state-info (row ?r) (col ?c) (group ?g) (diag ?d) (id ?x) (unused-values ?v)))
    (assert (maked (id ?x) (value ?v)))
)

(defrule add-to-state
    (declare (salience 10))
    (phase init-forward)
    
    (possible (id ?x) (value ?v))
    (not (maked (id ?x) (value ?v)))
    ?s <- (state-info (id ?x) (unused-values $?prev))
    =>
    (modify ?s (unused-values ?v $?prev))
    (assert (maked (id ?x) (value ?v)))
)

(defrule finished-initiating
    (declare (salience -10))
    
    ?fact <- (phase init-forward)
    =>
    (retract ?fact)
    (assert (phase remove-maked))
)

(defrule remove-maked
    (phase remove-maked)
    ?fact <- (maked)
    =>
    (retract ?fact)
)

(defrule finished-remove-maked
    ?fact <- (phase remove-maked)
    (not (maked))
    =>
    (retract ?fact)
    (assert (phase backtracking))
    (assert (level 1))
)

; ***********************
; Start guessing variable
; ***********************

(defrule consistency-check
    (phase consist-check)
    
    =>
    
    (assert (consistent yes))
)

(defrule row-consistentcy
    (declare (salience 10))
    
    (phase consist-check)
    (state-info (row ?r) (id ?id1) (value ?v&:(> ?v 0)))
    (state-info (row ?r) (id ?id2&~?id1) (value ?v))
    ?fact <- (consistent yes)
    
    =>
    
    (retract ?fact)
    (assert (consistent no))
)

(defrule col-consistentcy
    (declare (salience 10))
    
    (phase consist-check)
    (state-info (col ?c) (id ?id1) (value ?v&:(> ?v 0)))
    (state-info (col ?c) (id ?id2&~?id1) (value ?v))
    ?fact <- (consistent yes)
    
    =>
    
    (retract ?fact)
    (assert (consistent no))
)

(defrule group-consistentcy
    (declare (salience 10))
    
    (phase consist-check)
    (state-info (group ?g) (id ?id1) (value ?v&:(> ?v 0)))
    (state-info (group ?g) (id ?id2&~?id1) (value ?v))
    ?fact <- (consistent yes)
    
    =>
    
    (retract ?fact)
    (assert (consistent no))
)

(defrule diag-consistentcy
    (declare (salience 10))
    
    (phase consist-check)
    (state-info (diag ?d&:(> ?d 0)) (id ?id1) (value ?v&:(> ?v 0)))
    (state-info (diag ?d) (id ?id2&~?id1) (value ?v))
    ?fact <- (consistent yes)
    
    =>
    
    (retract ?fact)
    (assert (consistent no))
)

(defrule finished-checking
    (declare (salience -10))
    
    ?fact <- (phase consist-check)
    (consistent ?)
    
    =>
    
    (retract ?fact)
    (assert (phase after-check))
)

(defrule try-put1
    (declare (salience 10))
    ?fact2 <- (phase backtracking)
    
    (level ?l)
    
    ?fact <- (state-info (id ?l) (used-values) (value 0) (unused-values ?next $?behind))
    
    =>
    
    (duplicate ?fact (value ?next) (unused-values $?behind))
    (retract ?fact)
    (retract ?fact2)
    (assert (phase consist-check))
)

(defrule try-put
    (declare (salience 10))
    ?fact2 <- (phase backtracking)
    
    (level ?l)
    
    ?fact <- (state-info (id ?l) (used-values $?used) (value ?prev&:(> ?prev 0)) (unused-values ?next $?behind))
    
    =>
    
    (duplicate ?fact (used-values ?prev $?used) (value ?next) (unused-values $?behind))
    (retract ?fact)
    (retract ?fact2)
    (assert (phase consist-check))
)

(defrule next-level
    ?fact <- (phase after-check)
    ?fact1 <- (consistent yes)
    ?fact2 <- (level ?l)
    
    =>
    
    (retract ?fact1)
    (retract ?fact)
    (retract ?fact2)
    (assert (level (+ ?l 1)))
    (assert (phase backtracking))
)

(defrule cur-level
    ?fact <- (phase after-check)
    ?fact1 <- (consistent no)
    
    =>
    
    (retract ?fact1)
    (retract ?fact)
    (assert (phase backtracking))
)

(defrule cant-put
    (declare (salience -10))
    (phase backtracking)
    
    ?fact2 <- (level ?l)
    
    ?fact <- (state-info (id ?l) (value ?v) (used-values $?used))
    
    =>
    
    (duplicate ?fact (used-values) (value 0) (unused-values ?v $?used))
    (retract ?fact)
    (retract ?fact2)
    (assert (level (- ?l 1)))
)

(defrule finished-backtrack
    (declare (salience 20))
    (level ?l)
    (test (or (= ?l 0) (= ?l 37)))
    ?fact <- (phase backtracking)
    
    =>
    
    (retract ?fact)
    (assert (phase finished-backtrack))
)

(defrule eliminate-multi-sol
    (declare (salience 10))
    (phase finished-backtrack)
    (state-info (id ?id) (value ?v))
    ?fact <- (possible (id ?id) (value ?v1&~?v))
    
    =>
    
    (retract ?fact)
)

(defrule eliminate-multi-sol1
    (declare (salience 9))
    (phase finished-backtrack)
    ?fact <- (unsolved)
    
    =>
    
    (retract ?fact)
)

(defrule return-to-original
    ?fact <- (phase finished-backtrack)
    
    =>
    
    (retract ?fact)
    (assert (phase final-output))
    (assert (print-position 1 1))
)