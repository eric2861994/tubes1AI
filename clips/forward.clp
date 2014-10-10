(deftemplate state-info
    (slot id)
    (slot level)
    (slot value (default 0))
    (multislot possible-values)
)

(deftemplate maked
    (slot id)
    (slot value)
)

(deftemplate backtrack-state
    (slot level (default 0))
    (slot var-id (default 0))
)

; **********************
; initiating state zero
; **********************

(defrule make-new-state
    (declare (salience 10))
    (phase init-forward)
    
    (possible (id ?x) (value ?v))
    (not (maked (id ?x) (value ?v)))
    (not (state-info (id ?x)))
    =>
    (assert (state-info (id ?x) (possible-values ?v)))
    (assert (maked (id ?x) (value ?v)))
)

(defrule add-to-state
    (declare (salience 10))
    (phase init-forward)
    
    (possible (id ?x) (value ?v))
    (not (maked (id ?x) (value ?v)))
    ?s <- (state-info (id ?x) (possible-values $?prev))
    =>
    (modify ?s (possible-values $?prev ?v))
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
    (phase backtracking)
    (assert (backtrack-state))
)

; ***********************
; Start guessing variable
; ***********************
(defrule select-variable
    (declare (salience 10))
    (phase backtracking)
    
    ?fact <- (backtrack-state (level ?highest) (var-id 0))
    (not (backtrack-state (level ?alpha&:(> ?alpha ?highest))))
    
    (state-info (id ?mcv) (level ?highest) (possible-values $?pv))
    (not (state-info (id ?other&~?mcv) (level ?highest) (possible-values $?opv&:
    (< (length$ $?opv) (length$ $?pv)))))
    
    =>
    
    (modify $fact (var-id ?mcv)) ; milih variabel paling constrained
)

(defrule try-variable
    (declare (salience 10))
    (phase backtracking)
    
    (backtrack-state (level ?highest) (var-id ?var&:(> ?var 0)))
    (not (backtrack-state (level ?alpha&:(> ?alpha ?highest))))
    ?f <- (state-info (id ?var) (level ?highest) (possible-values $?pos?:(> (length$ $?pos) 0)))
    
    =>
    
    (modify ?f (value (nth$ 1 $?pos)) (possible-values (rest$ $?pos)))
    (phase forward-checking)
)

(defrule eliminate-possibility
    (phase forward-checking)
    (backtrack-state (level ?level) (var-id ?varid))
    (state-info (id ?id) (level ?level) (value ?value) (possible-values $?pv))
    
    
)

(defrule return-to-last
    (declare (salience -10))
    (phase backtracking)
    ?fact <- (backtrack-state (level ?highest))
    (not (backtrack-state (level ?alpha&:(> ?alpha ?highest))))
    
    =>
    
    (retract
)