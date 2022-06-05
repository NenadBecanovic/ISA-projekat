
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

INSERT INTO public.user_category(
    discount_percentage, name, points, version)
VALUES (7, 'Silver', 25, 1);

INSERT INTO public.user_category(
    discount_percentage, name, points, version)
VALUES (10, 'Bronze', 50, 1);

INSERT INTO public.user_category(
    discount_percentage, name, points, version)
VALUES (15, 'Gold', 80, 1);

INSERT INTO public.company(
    percentage__per_reservation, points_reservation_client, points_reservation_owner)
VALUES (5, 5, 3);

INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('jovanovicvladimir099@gmail', 'Vladimir', 0, true, false, false, 'Jovanović', 0, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'dovla', 1, 1, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('teodora.maruna@gmail.com', 'Teodora', 0, true, false, false, 'Maruna', 0, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'dovla1', 2, 2, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('isa2021mail@gmail.com', 'Janko', 0, true, false, false, 'Janković', 0, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'janko', 3, 1, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('marko6@gmail.com', 'Marko', 0, true, false, false, 'Marković', 0, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'marko', 6, 5, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('nenadbecanovic99@gmail.com', 'Nenad', 15, true, false, false, 'Bečanović', 3, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'nenad', 5, 4, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('mika@gmail.com', 'Mika', 0, true, false, false, 'Mikić', 0, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'mika', 4, 2, 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('jovan@gmail.com', 'Jovan', 18, true, false, false, 'Pavlović', 4, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, 'Ja mnogo volim da se družim sa ljudima i da ih vodim na pecanje!!! Dodjite da se super provedemo i upecamo nešto dobro', 0642581473, 0, '', 'jovan', 1, 4, 1);

INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, is_first_login, last_name, number_of_reviews, password, penalties, personal_description, phone_number, points, reason_for_registration, username, address_id, authority_id, category_id)
VALUES ('branko@gmail.com', 'Branko', 64, true, false, false, 'Branković', 15, '$2a$10$gKk075epwlKGzXU9uaBBcO8bI8h4dU3GukcJsxfRCpqty.zUW3eJS', 0, '', 0642581473, 0, '', 'branko', 2, 3, 1);


INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema pusenja', 2000, 0, false, 'Vikendica na Kopaoniku', 0, 2000, 'Odlicna vikendica', 1, 4, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema kasnjena', 2, 0, false, 'Vikendica na Zlatiboru', 0, 2000, 'Odlicna vikendica', 1, 5, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema galame poslije 12', 2, 0, false, 'Vikendica na Tari', 0, 2000, 'Odlicna vikendica', 1, 6, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema kupanja u reci!', 2, 0, false, 'Kuća na Dunavu', 0, 3000, 'Odlicna vikendica', 1, 4, 6);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, number_of_reviews, price_per_day, promo_description, version, address_id, owner_id)
VALUES ('Nema bacanja smeća u šumu!', 0, 0, true, 'Fruškogorska bajka', 0, 4500, 'Odlicna vikendica', 2, 6, 6);

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
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, number_of_reviews, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Dunavu', 0, 4000, 'Odlican brod', 'Kruzer', 1, 7, 1, 8);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, number_of_reviews, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 3, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Mlavi', 0, 4000, 'Odlican brod', 'Kruzer', 1, 8, 2, 8);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, number_of_reviews, price_per_day, promo_description, type, version, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 4, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Savi', 0, 4000, 'Odlican brod', 'Kruzer', 1, 9, 3, 8);

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
VALUES ('ŠTAPOVI', 3000);
INSERT INTO additional_services(
    name, price)
VALUES ('HRANA', 2000);
INSERT INTO additional_services(
    name, price)
VALUES ('PIĆE', 2000);
INSERT INTO additional_services(
    name, price)
VALUES ('SKI PASS', 9000);
INSERT INTO additional_services(
    name, price)
VALUES ('DOMAĆI DORUCAK', 1000);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Molimo vas da poštujete plan i program avanture.', 0, 5, 'Sva potrebna oprema.', 0, true, false, 'Pecanje Zaovinsko jezero', 0, 1500, 'Zaovinsko jezero je jedno od najlepših koje naša zemlja ima da ponudi! Idemo da pecamo i da se zezamo!', 6, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Samo opušteno', 0, 3, 'Sva potrebna oprema.(NOVO SVE)', 0, true, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Instruktor se mora poštovati.', 5, 5, 'Sva potrebna oprema.', 0, false, false, 'Avantura na Dunavu', 0, 1500, 'Dodjite da naučite da pecate,sjajno se družite i provedete prelep dan na reci!', 3, 5);

INSERT INTO public.fishing_adventure(
    behaviour_rules, cancalletion_fee, capacity, fishing_equipment, grade, is_cancalletion_free, is_deleted, name, number_of_reviews, price_per_hour, promo_description, address_id, instructor_id)
VALUES ('Nema kupanja!', 0, 5, 'Sva potrebna oprema.', 0, true, false, 'Pecanje na Savi', 0, 800, 'Obećavam vam da ćete se zaljubiti u pecanje!', 3, 7);

INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure1.jpg', null, 1, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure2.jpg', null, 1, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure3.jpg', null, 1, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure4.jpg', null, 2, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure5.jpg', null, 3, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure6.jpg', null, 4, null);
INSERT INTO public.image(
    image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/adventure2.jpg', null, 5, null);

INSERT INTO addiotional_services_adventure(
    additional_services_id, fishing_adventure_id)
VALUES (1,1);

INSERT INTO addiotional_services_adventure(
    additional_services_id, fishing_adventure_id)
VALUES (3,1);

INSERT INTO addiotional_services_adventure(
    additional_services_id, fishing_adventure_id)
VALUES (2,2);

INSERT INTO addiotional_services_adventure(
    additional_services_id, fishing_adventure_id)
VALUES (3,3);
