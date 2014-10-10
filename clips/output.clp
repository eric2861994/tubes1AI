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

;;; ############
;;; OUTPUT RULES
;;; ############

(defglobal ?*output* = t)

;;; *************
;;; print-initial
;;; *************

(defrule print-initial

   (declare (salience 10))

   (phase initial-output)

   =>

   (printout ?*output* crlf "The puzzle is: " crlf crlf "   "))

;;; ***********
;;; print-final
;;; ***********

(defrule print-final

   (declare (salience 10))

   (phase final-output)

   =>

   (printout ?*output* crlf "The solution is: " crlf crlf "   "))

;;; **************************
;;; print-position-value-found
;;; **************************

(defrule print-position-value-found

   (phase initial-output | final-output)

   (print-position ?r ?c)

   (possible (row ?r) (column ?c) (value ?v))

   (not (possible (row ?r) (column ?c) (value ~?v)))

   =>

   (assert (position-printed ?r ?c))

   (printout ?*output* ?v)
)

;;; ******************************
;;; print-position-value-not-found
;;; ******************************

(defrule print-position-value-not-found

   (declare (salience -5))

   (phase initial-output | final-output)

   (print-position ?r ?c)

   (not (position-printed ?r ?c))

   =>

   (assert (position-printed ?r ?c))

   (printout ?*output* *))

;;; ********************
;;; next-position-column
;;; ********************

(defrule next-position-column

   (declare (salience -10))

   (phase initial-output | final-output)

   ?f1 <- (print-position ?r ?c&:(< ?c 6))

   ?f2 <- (position-printed ?r ?c)

   =>

   (if (= (mod ?c 3) 0)
      then
      (printout ?*output* "  ")
      else
      (printout ?*output* " "))

   (retract ?f1 ?f2)

   (assert (print-position ?r (+ 1 ?c))))

;;; *****************
;;; next-position-row
;;; *****************

(defrule next-position-row

   (declare (salience -10))

   (phase initial-output | final-output)

   ?f1 <- (print-position ?r&:(< ?r 6) 6)

   ?f2 <- (position-printed ?r 6)

   =>

   (if (= (mod ?r 2) 0)
      then
      (printout ?*output* crlf crlf "   ")
      else
      (printout ?*output* crlf "   "))

   (retract ?f1 ?f2)

   (assert (print-position (+ 1 ?r) 1)))

;;; ************************
;;; output-done-rule-listing
;;; ************************

(defrule output-done-rule-listing

   (declare (salience -10))

   ?f1 <- (phase final-output)

   ?f2 <- (print-position 6 6)

   ?f3 <- (position-printed 6 6)

   (exists (technique-employed))

   =>

   (printout ?*output* crlf crlf "Rules used:" crlf crlf)

   (retract ?f1 ?f2 ?f3)

   (assert (phase list-rules)))

;;; ***************************
;;; output-done-no-rule-listing
;;; ***************************

(defrule output-done-no-rule-listing

   (declare (salience -10))

   (phase final-output)

   ?f1 <- (print-position 6 6)

   ?f2 <- (position-printed 6 6)

   (not (technique-employed))

   =>

   (printout ?*output* crlf)

   (retract ?f1 ?f2))

;;; *******************
;;; initial-output-done
;;; *******************

(defrule initial-output-done

   (declare (salience -10))

   (phase initial-output)

   ?f1 <- (print-position 6 6)

   ?f2 <- (position-printed 6 6)

   =>

   (printout ?*output* crlf)

   (retract ?f1 ?f2))

;;; *********
;;; list-rule
;;; *********

(defrule list-rule

   (phase list-rules)

   ?f <- (technique-employed (rank ?p) (reason ?reason))

   (not (technique-employed (rank ?p2&:(< ?p2 ?p))))

   =>

   (printout ?*output* "   " ?reason crlf)

   (retract ?f))

;;; **************
;;; list-rule-done
;;; **************

(defrule list-rule-done

   (declare (salience -10))

   ?f <- (phase list-rules)

   =>

   (printout ?*output* crlf)

   (retract ?f))