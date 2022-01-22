
INSERT INTO public.address(
	city, latitude, longitude, postal_code, state, street)
	VALUES ('Bijeljina', 0, 0, 76300, 'Bosna i Hercegovina', 'Dusana Baranina');
INSERT INTO public.address(
    city, latitude, longitude, postal_code, state, street)
VALUES ('Novi Sad', 0, 0, 76300, 'Srbija', 'Bulevar despota Stefana 16');
INSERT INTO public.address(
    city, latitude, longitude, postal_code, state, street)
VALUES ('Kragujevac', 0, 0, 76300,'Srbija', 'Danila Kisa 40');
INSERT INTO public.address(
	city, latitude, longitude, postal_code, state, street)
	VALUES ('Kopaonik', 0, 0, 76300, 'Srbija', 'Dusana Baranina');
INSERT INTO public.address(
    city, latitude, longitude, postal_code, state, street)
VALUES ('Zlatibor', 0, 0, 76300, 'Srbija', 'Bulevar despota Stefana 16');
INSERT INTO public.address(
    city, latitude, longitude, postal_code, state, street)
VALUES ('Tara', 0, 0, 76300,'Srbija', 'Danila Kisa 40');

INSERT INTO public.address(
	city, latitude, longitude, postal_code, state, street)
	VALUES ('Beograd', 0, 0, 76300, 'Srbija', 'Dok 16');
INSERT INTO public.address(
    city, latitude, longitude, postal_code, state, street)
VALUES ('Zrenjanin', 0, 0, 76300, 'Srbija', 'Dok 17');
INSERT INTO public.address(
    city, latitude, longitude, postal_code, state, street)
VALUES ('VItorog', 0, 0, 76300,'Srbija', 'Dok 19');

INSERT INTO public.authority(
    name)
VALUES ('ROLE_USER');
INSERT INTO public.authority(
    name)
VALUES ('ROLE_HOUSE_OWNER');
INSERT INTO public.authority(
    name)
VALUES ('ROLE_BOAT_OWNER');
INSERT INTO public.authority(
    name)
VALUES ('ROLE_INSTRUCTOR');
INSERT INTO public.authority(
    name)
VALUES ('ROLE_ADMINISTRATOR');

INSERT INTO public.user_category(
    discount_percentage, name, points, version)
VALUES (0, 'Basic', 0, 1);

INSERT INTO public.company(
    percentage__per_reservation, points_reservation_client, points_reservation_owner)
VALUES (5, 5, 3);

INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('jovanovicvladimir099@gmail', 'Vladimir', 0, true, false, false, 'Jovanovic', 0, '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, '', 0642581473, 0, '', 'dovla', 1, 1, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('tedora.maruna@gmail.com', 'Teodora', 0, true, false, false, 'Maruna', 0, '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, '', 0642581473, 0, '', 'dovla1', 2, 2, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('isa2021mail@gmail.com', 'Nenad', 0, true, false, false, 'Becanovic', 0, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', 0642581473, 0, '', 'user', 3, 1, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('nenadbecanovic6@gmail.com', 'Admin', 0, true, false, false, 'Becanovic', 0, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', 0642581473, 0, '', 'admin', 3, 5, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('nenadbecanovic99@gmail.com', 'Aaa', 0, true, false, false, 'Becanovic', 0, '$2a$10$hDhLiG.KPOQE84R.50d6iufgSeeYJ1IVsWYHJ0IrEpjtPSg6FgtTK', 0, '', 0642581473, 0, '', 'instructor', 3, 4, 1);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema pusenja', 2000, 0, false, 'Vikendica na Kopaoniku', 2000, 'Odlicna vikendica', 1, 4, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema kasnjena', 2000, 0, false, 'Vikendica na Zlatiboru', 2000, 'Odlicna vikendica', 1, 5, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema galame poslije 12', 2000, 0, false, 'Vikendica na Tari', 2000, 'Odlicna vikendica', 1, 6, 2);

INSERT INTO public.navigation_equipment(
    fish_finder, gps, radar, vhfradio)
VALUES (true, true, false, false);

INSERT INTO public.navigation_equipment(
    fish_finder, gps, radar, vhfradio)
VALUES (false, true, false, false);

INSERT INTO public.navigation_equipment(
    fish_finder, gps, radar, vhfradio)
VALUES (true, true, true, false);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2000, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Dunavu', 4000, 'Odlican brod', 'Kruzer', 1, 7, 1, 3);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2000, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Mlavi', 4000, 'Odlican brod', 'Kruzer', 1, 8, 2, 3);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2000, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Savi', 4000, 'Odlican brod', 'Kruzer', 1, 9, 3, 3);

INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/boat-inside2.jpg', 1, null, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/boat-inside2.jpg', 2, null, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/boat-inside2.jpg', 3, null, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/house3.jpg', null, null, 1);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/house3.jpg', null, null, 2);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/house3.jpg', null, null, 3);

INSERT INTO additional_services(
    name, price)
VALUES ('STAPOVI', 3000);
INSERT INTO additional_services(
    name, price)
VALUES ('HRANA', 2000);
INSERT INTO additional_services(
    name, price)
VALUES ('PICE', 2000);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Molimo vas da poštujete plan i program avanture.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Pecanje Zaovinsko jezero', 0, 1500, 'Zaovinsko jezero je jedno od najlepših koje naša zemlja ima da ponudi! Idemo da pecamo i da se zezamo!', 6, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);

--INSERT INTO public.adventure_reservation(
--    availability_period, end_date, has_appeal_owner, has_feedback_owner, has_report, is_action, is_available, is_cancelled, max_guests, price, start_date, fishing_adventure_id, guest_id);
--VALUES (false, '', false, false, false, false, false, false, 5, 7000, '', 1, 3);

INSERT INTO addiotional_services_adventure(
    additional_services_id, fishing_adventure_id)
VALUES (1,1);

INSERT INTO addiotional_services_adventure(
    additional_services_id, fishing_adventure_id)
VALUES (3,1);

--INSERT INTO additional_services_adventure_reservation(
--    additional_services_id, fishing_adventure_id)
--VALUES (3,1);
