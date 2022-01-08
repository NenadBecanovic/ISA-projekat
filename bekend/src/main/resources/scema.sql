
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

INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, last_name, password, penalties, phone_number, username, address_id)
VALUES ('jovanovicvladimir099@gmail', 'Vladimir', 0, true, false, 'Jovanovic', '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, 0642581473, 'dovla', 1);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, last_name, password, penalties, phone_number, username, address_id)
VALUES ('tedora.maruna@gmail.com', 'Teodora', 0, true, false, 'Maruna', '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, 0642581473, 'dovla1', 2);
INSERT INTO public.my_user(
    email, first_name, grade, is_activated, is_deleted, last_name, password, penalties, phone_number, username, address_id)
VALUES ('nenadbecanovic@gmail', 'Nenad', 0, true, false, 'Becanovic', '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, 0642581473, 'dovla2', 3);

INSERT INTO public.user_authorities(
    user_id, authority_id)
VALUES (1, 1);

INSERT INTO public.user_authorities(
    user_id, authority_id)
VALUES (2, 2);

INSERT INTO public.user_authorities(
    user_id, authority_id)
VALUES (2, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, price_per_day, promo_description, address_id, owner_id)
VALUES ('Nema pusenja', 2000, 0, false, 'Vikendica na Kopaoniku', 2000, 'Odlicna vikendica', 4, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, price_per_day, promo_description, address_id, owner_id)
VALUES ('Nema kasnjena', 2000, 0, false, 'Vikendica na Zlatiboru', 2000, 'Odlicna vikendica', 5, 2);

INSERT INTO public.house(
    behaviour_rules, cancalletion_fee, grade, is_cancalletion_free, name, price_per_day, promo_description, address_id, owner_id)
VALUES ('Nema galame poslije 12', 2000, 0, false, 'Vikendica na Tari', 2000, 'Odlicna vikendica', 6, 2);

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
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2000, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Dunavu', 4000, 'Odlican brod', 'Kruzer', 7, 1, 3);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2000, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Mlavi', 4000, 'Odlican brod', 'Kruzer', 8, 2, 3);

INSERT INTO public.boat(
    behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, grade, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
VALUES ('Nema alkohla na brodu', 2000, 10, 45645, 100, 'stapovi', 0, false, 100, 20, 'Kruzer na Savi', 4000, 'Odlican brod', 'Kruzer', 9, 3, 3);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/boat-inside2.jpg', 1, null, null);
INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/boat-inside2.jpg', 2, null, null);
INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/boat-inside2.jpg', 3, null, null);
INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/house3.jpg', null, null, 1);
INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/house3.jpg', null, null, 2);
INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES ('assets/house3.jpg', null, null, 3);
