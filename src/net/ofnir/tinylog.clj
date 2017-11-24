(ns net.ofnir.tinylog)

(set! clojure.core/*warn-on-reflection* true)

(defmacro -deflogger
  [fn-name]
  `(defmacro ~fn-name
     [~'& ~'args]
     `(.  ~'org.pmw.tinylog.Logger ~@(conj ~'args '~fn-name))))

(-deflogger error)
(-deflogger warn)
(-deflogger info)
(-deflogger debug)
(-deflogger trace)

(defn supplier
  [f]
  (reify
    org.pmw.tinylog.Supplier
    (get [this]
      (f))))

(defn suppliers
  [fs]
  (into-array (map supplier fs)))

; some examples
#_ (info "Hello world")
#_ (info "Hello {}" (suppliers [(constantly "world")]))
#_ (info "Hello {}" (into-array ["world"]))
#_ (info (ex-info "Exploded" {}) "Failed to not explode")
