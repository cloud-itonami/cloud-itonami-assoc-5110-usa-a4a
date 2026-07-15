(ns association.facts
  "Industry rule/standard catalog for Airlines for America (A4A, formerly
  the Air Transport Association of America, Wikidata Q408816) -- a 15th
  industry-association-level source (see cloud-itonami-assoc-6419-jpn-zenginkyo,
  -6512-jpn-sonpo, -6612-jpn-jsda, -6419-deu-bankenverband, -6612-usa-finra,
  -6512-usa-naic, -6920-jpn-jicpa, -6920-usa-aicpa, -6419-fra-fbf,
  -6511-jpn-seiho, -6910-jpn-nichibenren, -6810-jpn-recaj, -6411-jpn-boj,
  -6120-usa-ctia for the first fourteen) per ADR-2607141700
  (cloud-itonami-compliance-fact-federation). The FIRST entry aligned to
  ISIC 5110 (passenger air transport) -- a new industry code for this
  family. A rule not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.

  Both entries were directly WebFetch-verified: Spec 2000, A4A's
  aviation-industry data-exchange standards suite (published jointly
  with the ATA e-Business Program at ataebiz.org), which is a genuine
  TECHNICAL STANDARD rather than a compliance rule/law/code -- a new
  `:kind :technical-standard` is introduced to preserve this distinction
  rather than reusing `:self-regulatory-code`; and A4A's own History
  page (airlines.org), confirming the organization's 1936 founding as
  the Air Transport Association.")

(def catalog
  "assoc-slug -> vector of self-regulatory rule entries."
  {"a4a"
   [{:association-rule/id "a4a.spec-2000"
     :association-rule/title "Spec 2000: Data Exchange Standards"
     :association-rule/association "a4a"
     :association-rule/isic "5110"
     :association-rule/country "USA"
     :association-rule/kind :technical-standard
     :association-rule/url "https://ataebiz.org/spec-2000/"
     :association-rule/url-provenance :official-association-site
     :association-rule/retrieved-at "2026-07-15"
     :association-rule/topic #{:interoperability :standards}}
    {:association-rule/id "a4a.history-profile"
     :association-rule/title "History (organization profile)"
     :association-rule/association "a4a"
     :association-rule/isic "5110"
     :association-rule/country "USA"
     :association-rule/kind :governance-program
     :association-rule/url "https://www.airlines.org/who-we-are/history/"
     :association-rule/url-provenance :official-association-site
     :association-rule/established-date "1936"
     :association-rule/retrieved-at "2026-07-15"
     :association-rule/topic #{:governance}}]})

(defn spec-basis [assoc-slug] (get catalog assoc-slug))

(defn coverage
  ([] (coverage (keys catalog)))
  ([slugs]
   (let [have (filter catalog slugs)
         missing (remove catalog slugs)]
     {:requested (count slugs)
      :covered (count have)
      :covered-associations (vec (sort have))
      :missing-associations (vec (sort missing))
      :note (str "cloud-itonami-assoc-5110-usa-a4a Wave 0 (ADR-2607141700): "
                 (count (get catalog "a4a")) " a4a entries seeded with an "
                 "official airlines.org/ataebiz.org citation. Extend "
                 "`association.facts/catalog`, never fabricate a rule id/url.")})))

(defn by-topic [assoc-slug topic]
  (filterv #(contains? (:association-rule/topic %) topic) (spec-basis assoc-slug)))
