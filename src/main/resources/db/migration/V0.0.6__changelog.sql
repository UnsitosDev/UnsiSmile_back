ALTER TABLE odontograms
    ADD COLUMN fk_patient  bigint(20) NOT NULL,
ADD COLUMN fk_form_section bigint(20) NOT NULL,
ADD CONSTRAINT fk_patients FOREIGN KEY (fk_patient) REFERENCES patients(id_patient),
ADD CONSTRAINT fk_form_sections FOREIGN KEY (fk_form_section) REFERENCES form_sections(id_form_section);