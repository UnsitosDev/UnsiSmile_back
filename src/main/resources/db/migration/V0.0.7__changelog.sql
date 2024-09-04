drop table if exists hereditary_family_history;
drop table if exists hereditary_family_history_questions;
drop table if exists closed_answers_non_pathological;
drop table if exists closed_questions_pathological_antecedents;
drop table if exists open_answers_non_pathological;
drop table if exists open_questions_pathological_antecedents;
alter table patients
DROP FOREIGN KEY FK4pvbm0k7mrhj4859h5o6fnno;
alter table patients drop column fk_medical_history;
drop table if exists medical_histories;
drop table if exists non_pathological_personal_antecedents;
drop table if exists facial_exam;
drop table if exists facial_profile;
drop table if exists facial_front;
drop table if exists vital_signs;

