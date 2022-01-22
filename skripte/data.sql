

INSERT INTO public.additional_services (name, price) VALUES ('ŠTAPOVI', 3000);
INSERT INTO public.additional_services (name, price) VALUES ('HRANA', 2000);
INSERT INTO public.additional_services (name, price) VALUES ('PIĆE', 2000);
INSERT INTO public.additional_services (name, price) VALUES ('SKI PASS', 9000);
INSERT INTO public.additional_services (name, price) VALUES ( 'DOMAĆI DORUCAK', 1000);
INSERT INTO public.additional_services (name, price) VALUES ('WiFi', 1200);
INSERT INTO public.additional_services (name, price) VALUES ('BAZEN', 1200);
INSERT INTO public.additional_services (name, price) VALUES ('WiFi', 2000);
INSERT INTO public.additional_services (name, price) VALUES ('ROSTILJ', 1400);
INSERT INTO public.additional_services (name, price) VALUES ('BAZEN', 2000);
INSERT INTO public.additional_services (name, price) VALUES ('OPREMA', 1240);


INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Bijeljina', 0, 0, 76300, 'Bosna i Hercegovina', 'Dusana Baranina');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Novi Sad', 0, 0, 76300, 'Srbija', 'Bulevar despota Stefana 16');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Kragujevac', 0, 0, 76300, 'Srbija', 'Danila Kisa 40');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Tara', 0, 0, 76300, 'Srbija', 'Danila Kisa 40');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Vitorog', 0, 0, 76300, 'Srbija', 'Dok 19');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Beograd', 0, 0, 76300, 'Srbija', 'Dok 16');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Kopaonik', 45, 19, 76300, 'Srbija', 'Dusana Baranina');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Zlatibor', 45.246693, 20, 76300, 'Srbija', 'Bulevar despota Stefana 16');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Zrenjanin', 45, 19.835608, 76300, 'Srbija', 'Dok 17');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Beograd', 45, 19, 76300, 'Srbija', 'Dok 16');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Beograd', 45.235683, 19, 76300, 'Srbija', 'Dok 16');
INSERT INTO public.address (city, latitude, longitude, postal_code, state, street) VALUES ('Zrenjanin', 45, 19, 76300, 'Srbija', 'Dok 17');


INSERT INTO public.authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO public.authority (id, name) VALUES (2, 'ROLE_HOUSE_OWNER');
INSERT INTO public.authority (id, name) VALUES (3, 'ROLE_BOAT_OWNER');
INSERT INTO public.authority (id, name) VALUES (4, 'ROLE_INSTRUCTOR');
INSERT INTO public.authority (id, name) VALUES (5, 'ROLE_ADMINISTRATOR');


INSERT INTO public.user_category (discount_percentage, name, points, version) VALUES (0, 'Basic', 0, 1);
INSERT INTO public.user_category (discount_percentage, name, points, version) VALUES (7, 'Silver', 25, 1);
INSERT INTO public.user_category (discount_percentage, name, points, version) VALUES (10, 'Bronze', 50, 1);
INSERT INTO public.user_category (discount_percentage, name, points, version) VALUES (15, 'Gold', 80, 1);


INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('isa2021mail@gmail.com', 'Janko', 0, true, false, false, 'Janković', 0, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', '642581473', 0, '', 'janko', 3, 1, 1);
INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('marko6@gmail.com', 'Marko', 0, true, false, false, 'Marković', 0, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', '642581473', 0, '', 'marko', 6, 5, 1);
INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('nenadbecanovic99@gmail.com', 'Nenad', 15, true, false, false, 'Bečanović', 3, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', '642581473', 0, '', 'nenad', 5, 4, 1);
INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('mika@gmail.com', 'Mika', 0, true, false, false, 'Mikić', 0, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', '642581473', 0, '', 'mika', 4, 2, 1);
INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('jovan@gmail.com', 'Jovan', 18, true, false, false, 'Pavlović', 4, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, 'Ja mnogo volim da se družim sa ljudima i da ih vodim na pecanje!!! Dodjite da se super provedemo i upecamo nešto dobro', '642581473', 0, '', 'jovan', 1, 4, 1);
INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('branko@gmail.com', 'Branko', 64, true, false, false, 'Branković', 15, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', '642581473', 0, '', 'branko', 2, 3, 1);
INSERT INTO public.my_user (email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('teodora.maruna@gmail.com', 'Teodora', 0, true, false, false, 'Maruna', 0, '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, '', '642581473', 3, '', 'dovla1', 2, 2, 1);
INSERT INTO public.my_user ( email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id) VALUES ('jovanovicvladimir099@gmail.com', 'Vladimir', 0, true, false, false, 'Jovanović', 0, '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, '', '642581473', 15, '', 'dovla', 1, 1, 1);


INSERT INTO public.fishing_adventure (behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id) VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);
INSERT INTO public.fishing_adventure (behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id) VALUES ('Molimo vas da poštujete plan i program avanture.', 0, 5, 'Sva potrebna oprema.', 0, true, false, 'Pecanje Zaovinsko jezero', 0, 1500, 'Zaovinsko jezero je jedno od najlepših koje naša zemlja ima da ponudi! Idemo da pecamo i da se zezamo!', 6, 5);
INSERT INTO public.fishing_adventure (behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id) VALUES ('Samo opušteno', 0, 3, 'Sva potrebna oprema.(NOVO SVE)', 0, true, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);
INSERT INTO public.fishing_adventure (behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id) VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);
INSERT INTO public.fishing_adventure (behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id) VALUES ('Nema kupanja!', 0, 5, 'Sva potrebna oprema.', 0, true, false, 'Pecanje na Savi', 0, 800, 'Obećavam vam da ćete se zaljubiti u pecanje!', 3, 7);


INSERT INTO public.addiotional_services_adventure (additional_services_id, fishing_adventure_id) VALUES (1, 1);
INSERT INTO public.addiotional_services_adventure (additional_services_id, fishing_adventure_id) VALUES (3, 1);
INSERT INTO public.addiotional_services_adventure (additional_services_id, fishing_adventure_id) VALUES (2, 2);
INSERT INTO public.addiotional_services_adventure (additional_services_id, fishing_adventure_id) VALUES (3, 3);


INSERT INTO public.navigation_equipment (fish_finder, gps, radar, vhfradio) VALUES (false, true, false, false);


INSERT INTO public.boat (behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, number_of_reviews, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id) VALUES ('Nema alkohla na brodu', 5, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Mlavi', 0, 4000, 'Odlican brod', 'Kruzer', 1, 8, 2, 8);
INSERT INTO public.addiotional_services_boat (additional_services_id, boat_id) VALUES (12, 2);


INSERT INTO public.boat_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, boat_id, guest_id) VALUES (false, '2022-01-26 22:59:00', false, false, false, false, true, true, false, 4, 3400, '2022-01-23 22:59:00', 2, NULL);
INSERT INTO public.boat_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, boat_id, guest_id) VALUES (false, '2022-03-19 22:59:00', false, false, false, false, true, true, false, 5, 3400, '2022-03-13 22:59:00', 2, NULL);


INSERT INTO public.addiotional_services_boat_reservation (additional_services_id, boat_reservation_id) VALUES (12, 4);
INSERT INTO public.addiotional_services_boat_reservation (additional_services_id, boat_reservation_id) VALUES (12, 5);


INSERT INTO public.house (behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id) VALUES ('Nema pusenja', 2, 0, false, 'Vikendica na Kopaoniku', 0, 2000, 'Odlicna vikendica', 1, 4, 2);
INSERT INTO public.house (behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id) VALUES ('Nema kasnjena', 4, 0, false, 'Vikendica na Zlatiboru', 0, 2000, 'Odlicna vikendica', 1, 5, 2);
INSERT INTO public.house (behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id) VALUES ('Nema kupanja u reci!', 5, 0, false, 'Kuća na Dunavu', 0, 3000, 'Odlicna vikendica', 1, 11, 6);
INSERT INTO public.house (behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id) VALUES ('Nema bacanja smeća u šumu!', 0, 0, true, 'Fruškogorska bajka', 0, 4500, 'Odlicna vikendica', 2, 10, 6);


INSERT INTO public.addiotional_services_house (additional_services_id, house_id) VALUES (6, 1);
INSERT INTO public.addiotional_services_house (additional_services_id, house_id) VALUES (7, 2);
INSERT INTO public.addiotional_services_house (additional_services_id, house_id) VALUES (8, 4);
INSERT INTO public.addiotional_services_house (additional_services_id, house_id) VALUES (9, 4);
INSERT INTO public.addiotional_services_house (additional_services_id, house_id) VALUES (10, 5);



INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (false, '2022-02-24 22:49:00', false, false, false, false, true, true, false, 2, 1500, '2022-02-22 22:49:00', NULL, 1);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (true, '2022-04-21 22:51:00', false, false, false, false, false, false, false, 0, 0, '2022-04-10 22:51:00', NULL, 2);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (false, '2022-01-27 22:51:00', false, false, false, false, true, true, false, 1, 1300, '2022-01-24 22:51:00', NULL, 2);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (false, '2022-02-18 22:55:00', false, false, false, false, true, true, false, 3, 1600, '2022-02-14 22:55:00', NULL, 4);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (false, '2022-02-11 22:56:00', false, false, false, false, true, true, false, 2, 2300, '2022-02-06 22:56:00', NULL, 5);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (false, '2022-02-19 22:56:00', false, false, false, false, true, true, false, 5, 3000, '2022-02-14 22:56:00', NULL, 5);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES (false, '2022-01-25 22:49:00', false, false, false, false, true, false, false, 2, 2000, '2022-01-22 22:49:00', 1, 1);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES ( false, '2022-03-20 22:01:00', false, false, false, false, false, false, false, 2, 0, '2022-03-17 22:01:00', 1, 4);
INSERT INTO public.house_reservation (availability_period, end_date, has_appeal_entity, has_appeal_owner, has_feedback_entity, has_feedback_owner, is_action, is_available, is_cancelled, max_guests, price, start_date, guest_id, house_id) VALUES ( false, '2022-03-15 23:03:00', false, false, false, false, false, false, false, 1, 1200, '2022-03-12 23:03:00', 1, 1);

INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (6, 3);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (6, 4);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (7, 8);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (8, 12);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (9, 12);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (10, 15);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (10, 16);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (8, 18);
INSERT INTO public.addiotional_services_house_reservation (additional_services_id, house_reservation_id) VALUES (6, 19);


INSERT INTO public.company (percentage__per_reservation, points_reservation_client, points_reservation_owner) VALUES (5, 5, 3);


INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/boat3.jpg', 2, NULL, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/house1.jpg', NULL, NULL, 1);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/house2.jpg', NULL, NULL, 2);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/adventure1.jpg', NULL, 1, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/adventure2.jpg', NULL, 1, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/adventure3.jpg', NULL, 1, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/adventure4.jpg', NULL, 2, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/adventure5.jpg', NULL, 3, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ( 'assets/adventure6.jpg', NULL, 4, NULL);
INSERT INTO public.image (image_url, boat_id, fishing_adventure_id, house_id) VALUES ('assets/adventure2.jpg', NULL, 5, NULL);


INSERT INTO public.room (number_of_beds, house_id) VALUES (2, 1);
INSERT INTO public.room (number_of_beds, house_id) VALUES (3, 1);
INSERT INTO public.room (number_of_beds, house_id) VALUES (1, 2);
INSERT INTO public.room (number_of_beds, house_id) VALUES (3, 2);
INSERT INTO public.room (number_of_beds, house_id) VALUES (2, 4);
INSERT INTO public.room (number_of_beds, house_id) VALUES (4, 4);
INSERT INTO public.room (number_of_beds, house_id) VALUES (5, 4);
INSERT INTO public.room (number_of_beds, house_id) VALUES (4, 5);
INSERT INTO public.room (number_of_beds, house_id) VALUES (5, 5);


INSERT INTO public.subscription (owner_id, subscribed_user_id) VALUES (2, 1);

