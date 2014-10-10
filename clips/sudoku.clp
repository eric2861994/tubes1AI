;;; Tugas Besar I: Inteligensia Buatan
;;; Anggota:
;;;     Kevin Yudi Utama        (13512010)
;;;     Eric                    (13512021)
;;;     Ramandika Pranamulia    (13512078)
;;;     Stanley Santoso         (13512086)
;;;     Edmund Ophie            (13512095)

;;; Version 1.1
;;; Dikembangkan dari project Sudoku persegi
;;; Alamat situs project: http://sourceforge.net/p/clipsrules/code/HEAD/tree/examples/sudoku/

;;; Perubahan signifikan:
;;;     tidak lagi terdapat property size, karena dikembangkan
;;;     terdapat property diag yang menandakan diagonal mana suatu kotak berada [0,1,2]

;;; #######################
;;; DEFTEMPLATES & DEFFACTS
;;; #######################

(deftemplate possible
   (slot row)
   (slot column)
   (slot value)
   (slot group)
   (slot diag) ; ditambah untuk pengecekan diagonal
   (slot id))

(deftemplate impossible
   (slot id)
   (slot value)
   (slot rank)
   (slot reason))

(deftemplate technique-employed
   (slot reason)
   (slot rank))

(deftemplate technique
   (slot name)
   (slot rank))

(deftemplate iterate-rc
   (slot row)
   (slot column)
   (slot index))

(deftemplate rank
   (slot value)
   (slot process))

(deftemplate unsolved
   (slot row)
   (slot column))

;;; ###########
;;; SETUP RULES
;;; ###########

;;; **********
;;; initialize
;;; **********

(defrule initialize

   =>

   (assert (phase grid-values))

   (assert (pos-value 1))
   (assert (pos-value 2))
   (assert (pos-value 3))
   (assert (pos-value 4))
   (assert (pos-value 5))
   (assert (pos-value 6))
)

;;; *****************
;;; enable-techniques
;;; *****************

(defrule enable-techniques

   (declare (salience 10))

   (phase match)

   (not (possible (value any)))

   (not (rank))

   =>

   (assert (rank (value 1) (process yes))))


;;; ****************
;;; expand-any-start
;;; ****************

(defrule expand-any-start

   (declare (salience 10))

   (phase expand-any)

   (possible (row ?r) (column ?c) (value any) (id ?id))

   (not (possible (value any) (id ?id2&:(< ?id2 ?id))))

   =>

   (assert (iterate-rc (row ?r) (column ?c) (index 1))))

;;; **********
;;; expand-any
;;; **********

(defrule expand-any

   (declare (salience 10))

   (phase expand-any)

   (possible (row ?r) (column ?c) (value any) (group ?g) (diag ?d) (id ?id))

   (not (possible (value any) (id ?id2&:(< ?id2 ?id))))

   ?f <- (iterate-rc (row ?r) (column ?c) (index ?v))

   (pos-value ?v)

   (not (possible (row ?r) (column ?c) (value ?v)))

   =>

   (assert (possible (row ?r) (column ?c) (value ?v) (group ?g) (diag ?d) (id ?id)))

   (modify ?f (index (+ ?v 1))))

;;; *****************
;;; position-expanded
;;; *****************

(defrule position-expanded

   (declare (salience 10))

   (phase expand-any)

   ?f1 <- (possible (row ?r) (column ?c) (value any))

   ?f2 <- (iterate-rc (row ?r) (column ?c) (index ?v))

   (not (pos-value ?v))

   =>

   (assert (unsolved (row ?r) (column ?c)))

   (retract ?f1 ?f2))

;;; ###########
;;; PHASE RULES
;;; ###########

;;; ***************
;;; expand-any-done
;;; ***************

(defrule expand-any-done

   (declare (salience 10))

   ?f <- (phase expand-any)

   (not (possible (value any)))

   =>

   (retract ?f)

   (assert (phase initial-output))
   (assert (print-position 1 1)))

;;; ***********
;;; begin-match
;;; ***********

(defrule begin-match

   (declare (salience -20))

   ?f <- (phase initial-output)

   =>

   (retract ?f)

   (assert (phase match)))

;;; *****************
;;; begin-elimination
;;; *****************

(defrule begin-elimination

   (declare (salience -20))

   ?f <- (phase match)

   (not (not (impossible)))

   =>

   (retract ?f)

   (assert (phase elimination)))

;;; ******************
;;; next-rank-unsolved
;;; ******************

(defrule next-rank-unsolved

   (declare (salience -20))

   (phase match)

   (not (impossible))

   (rank (value ?last))

   (not (rank (value ?p&:(> ?p ?last))))

   (technique (rank ?next&:(> ?next ?last)))

   (not (technique (rank ?p&:(> ?p ?last)&:(< ?p ?next))))

   (exists (unsolved))

   =>

   (assert (rank (value ?next) (process yes))))

;;; **********************
;;; next-rank-not-unsolved
;;; **********************

(defrule next-rank-not-unsolved

   (declare (salience -20))

   (phase match)

   (not (impossible))

   (rank (value ?last))

   (not (rank (value ?p&:(> ?p ?last))))

   (technique (rank ?next&:(> ?next ?last)))

   (not (technique (rank ?p&:(> ?p ?last)&:(< ?p ?next))))

   (not (unsolved))

   =>

   (assert (rank (value ?next) (process no))))

;;; ************
;;; begin-output
;;; ************

(defrule begin-output

   (declare (salience -20))

   ?f <- (phase match)

   (not (impossible))

   (rank (value ?last))

   (not (rank (value ?p&:(> ?p ?last))))

   (not (technique (rank ?next&:(> ?next ?last))))

   =>

   (retract ?f)

   (assert (phase final-output))
   (assert (print-position 1 1)))
