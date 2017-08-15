INSERT INTO course (course_id, name, name_short) VALUES
  (1, 'Informacioni sistemi i tehnologije', 'ISIT'),
  (2, 'Menadzment', 'ME'),
  (3, 'Operacioni menadzment', 'OM'),
  (4, 'Menadzment kvaliteta i standardizacija', 'MK'),
  (5, 'Softversko inzenjerstvo i racunarske nauke', 'SIRN');

-- --------------------------------------------------------

INSERT INTO studies (studies_id, name, name_short) VALUES
  (1, 'Osnovne studije', 'OS'),
  (2, 'Master studije', 'MS'),
  (3, 'Doktroske studije', 'DS');

-- --------------------------------------------------------

INSERT INTO course_studies (studies_id, course_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (2, 5);

-- --------------------------------------------------------

INSERT INTO field_of_study (field_of_study_id, name) VALUES
  (1, 'Softversko inzenjerstvo'),
  (2, 'Vestacka inteligencija'),
  (3, 'Baze podataka'),
  (4, 'Cloud tehnologije'),
  (5, 'Mobilno racunarstvo'),
  (6, 'masinsko ucenje'),
  (7, 'poslovna inteligencija'),
  (8, 'algoritmi'),
  (9, 'ekspertni sistemi'),
  (10, 'racunarske mreze'),
  (11, 'natural language processing'),
  (12, 'procesiranje prirodnog jezika'),
  (13, 'korisnicki interfejs');

-- --------------------------------------------------------

INSERT INTO tag (tag_id, value) VALUES
  (1, 'muzika'),
  (2, 'ai'),
  (3, 'ml'),
  (4, 'umrezavanje'),
  (5, 'ekspertnisistem'),
  (6, 'ekspertnisistemi'),
  (7, 'nlp'),
  (8, 'interfejs'),
  (9, 'uml'),
  (10, 'gui');

-- --------------------------------------------------------

INSERT INTO user (user_id, biography, first_name, interests, last_name, students_transcript, email, is_admin, password, course_id)
VALUES
  (1, 'Administrator account', 'Administrator', '', 'A', '', 'admin@gmail.com', b'1',
   '$2a$10$5zmi/x3q534VHlDq/TBYiOv1JlofO2fMFY4T69XaOnWuQaohsonmG', NULL),
  (2, 'Igor Jovic je student master studija na Fakultetu Organizacionih Nauka, smer Softversko Inzenjerstvo', 'Igor',
   'Vestacka inteligencija, masinsko ucenje, softversko inzenjerstvo, cloud tehnologije', 'Jovic', '122/12',
   'jovic.i24@gmail.com', b'0', '$2a$10$YiYHJ3CGB554vRBGU9rhcezuB9FlDryxr5b3PFqRoRsFiEekzkthe', 1),
  (3, '', 'Vladan', '', 'Devedzic', '', 'devedzic@gmail.com', b'1',
   '$2a$10$/XPLXMG4ZGwkC0fX94yQcu.8hLjQfC2fdAsBTo4QuchZpiqjdLhL2', NULL);


INSERT INTO thesis (thesis_id, date_posted, defense_date, description, grade, mentor_email, mentor_name, name, user_email, user_name, view_count, course_id, file_id, user_mentor_id, user_id)
VALUES
  (1, '2017-08-12 15:40:51', '2017-09-10 00:00:00', '', 9, 'devedzic@gmail.com', 'Vladan Devedzic',
      'Masinsko ucenje i preporuka muzike', 'jovic.i24@gmail.com', 'Igor Jovic', 2, 1, NULL, 3, 2),
  (2, '2017-08-12 15:52:50', '2017-09-01 00:00:00', '', 10, NULL, 'Bojan Tomic',
      'Ekspertni sistem za pomoc pri postavljanju racunarske mreze', NULL, 'Aleksandar Rajkovic', 0, 1, NULL, NULL,
   NULL),
  (3, '2017-08-12 15:54:08', '2017-09-02 00:00:00', '', 10, NULL, 'Jelena Jovanovic',
      'Prepoznavanje entiteta u tekstu na primeru govora poslanika Skupstine Republike Srbije', NULL,
      'Branislav Vidojevic', 0, 1, NULL, NULL, NULL),
  (4, '2017-08-12 15:55:16', '2017-08-06 00:00:00', '', 10, '', 'Jelena Jovanovic',
      'Sistem za preporuku relevantnih Web sadrzaja zasnovan na analizi grafa atributa sadrzaja', '',
      'Tihomir Radosavljevic', 2, 5, NULL, NULL, NULL),
  (5, '2017-08-12 15:57:48', '2015-05-08 00:00:00', '', 10, NULL, 'Vladan Devedzic',
      'Automatsko generisanje korisnickog interfejsa aplikacije zasnovano na slucajevima koriscenja', NULL,
      'Ilija Antovic', 3, 5, NULL, 3, NULL);

-- --------------------------------------------------------

INSERT INTO thesis_field_of_study (thesis_id, field_of_study_id) VALUES
  (2, 1),
  (5, 1),
  (1, 2),
  (2, 2),
  (3, 2),
  (5, 2),
  (1, 3),
  (1, 6),
  (2, 6),
  (3, 6),
  (1, 7),
  (2, 7),
  (1, 8),
  (2, 8),
  (2, 9),
  (2, 10),
  (3, 11),
  (3, 12),
  (5, 13);

-- --------------------------------------------------------

INSERT INTO thesis_tag (thesis_id, tag_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (3, 2),
  (5, 2),
  (1, 3),
  (2, 3),
  (2, 6),
  (3, 7),
  (5, 8),
  (5, 9),
  (5, 10);
