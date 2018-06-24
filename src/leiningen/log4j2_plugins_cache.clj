(ns leiningen.log4j2-plugins-cache
  (:import
    [java.io File FileOutputStream]
    [java.util Collections]
    [org.apache.commons.io IOUtils]
    [org.apache.logging.log4j.core.config.plugins.processor PluginCache])
  (:require 
    [robert.hooke :as hooke]))

(defn load-cache-file
  [input-stream]
  (let [temp-file (File/createTempFile "Log4j2Plugins" "dat")
        ostream (FileOutputStream. temp-file)]
    (try 
      (IOUtils/copyLarge input-stream ostream)
      (finally
        (IOUtils/closeQuietly ostream)))
    (println "Loaded cache file:" (pr-str temp-file))
    [temp-file]))

(defn merge-cache-file
  [files merged]
  (println "Merge cache file:" (pr-str files) (pr-str merged))
  (concat files merged))

(defn write-cache-file
  [ostream files]
  (let [urls (Collections/enumeration (map #(.toURL (.toURI %)) files))
        plugin-cache (PluginCache.)]
    (.loadCacheFiles plugin-cache urls)
    (.writeCache plugin-cache ostream))
  (doseq [file files]
    (.delete file))
  (println "Wrote merged cache file"))

(defn middleware
  [project]
  (println "adding log4j2-plugins-cache middleware")
  (update project :uberjar-merge-with
          assoc #"^META-INF\/org\/apache\/logging\/log4j\/core\/config\/plugins\/Log4j2Plugins.dat$" 
          ['leiningen.log4j2-plugins-cache/load-cache-file 'leiningen.log4j2-plugins-cache/merge-cache-file 'leiningen.log4j2-plugins-cache/write-cache-file]))
