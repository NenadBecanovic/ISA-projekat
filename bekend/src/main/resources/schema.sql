INSERT INTO public.address(
    id, city, latitude, longitude, postal_code, state, street)
VALUES (1, 'Novi Sad', '45.23568473076801', '19.832957469234934', 21000, 'Srbija', 'Bulevar despota Stefana 16');

INSERT INTO address(
    id, city, latitude, longitude, postal_code, state, street)
VALUES (2, 'Novi Sad', '45.24669371442071', '19.83560809979456', 21000, 'Srbija', 'Danila Kisa 40');

INSERT INTO my_user(
    id, email, first_name, is_activated, last_name, password, phone_number, username, address_id)
VALUES (1, 'jovanovicvladimir099@gmail.com', 'Vladimir', 'true', 'Jovanovic', '$2a$10$g/Co4LSLQ1BfKzYoY4yPSeUsjefzQvj1/P6gEaxRrTLuZu7LSloBa',
        '00381642581473', 'dovla', 1);

-- INSERT INTO additional_services(
--     id, name, price)
-- VALUES (1, 'BAZEN', 3000);
-- INSERT INTO additional_services(
--     id, name, price)
-- VALUES (2, 'ROSTILJ', 2000);
-- INSERT INTO additional_services(
--     id, name, price)
-- VALUES (3, 'MASKA', 3000);
-- INSERT INTO additional_services(
--     id, name, price)
-- VALUES (4, 'PERAJA', 2000);
-- INSERT INTO additional_services(
--     id, name, price)
-- VALUES (5, 'PESKIRI', 2400);

INSERT INTO house(
    id, behaviour_rules, cancalletion_fee, is_cancalletion_free, name, price_per_day, promo_description, address_id, owner_id)
    VALUES (1, 'Zabranjeno pusenje.', 2000, false, 'Vikendica na Tari', 5000, 'Najbolja vikendica na Tari. Ocenjena sa 5 zvezdica od svakog klijenta koji je poseti!', 2, null);

-- INSERT INTO room(
--     id, number_of_beds, house_id)
--     VALUES (1, 3, 1);

-- INSERT INTO public.room(
--     id, number_of_beds, house_id)
-- VALUES (2, 2, 1);

INSERT INTO public.room(
    id, number_of_beds, house_id)
VALUES (3, 3, 1);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
    VALUES (1, 'assets/house1.jpg', null, null, 1);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES (2, 'assets/house2.jpg', null, null, 1);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES (3, 'assets/house3.jpg', null, null, 1);

-- INSERT INTO public.addiotional_services_house(
--     additional_services_id, house_id)
--     VALUES (1, 1);

-- INSERT INTO public.addiotional_services_house(
--     additional_services_id, house_id)
-- VALUES (2, 1);

INSERT INTO public.authority(
    id, name)
    VALUES (1, 'ROLE_USER');

INSERT INTO public.authority(
    id, name)
VALUES (2, 'ROLE_HOUSE_OWNER');

INSERT INTO public.authority(
    id, name)
VALUES (3, 'ROLE_BOAT_OWNER');

INSERT INTO public.navigation_equipment(
    id, vhfradio, fish_finder, gps, radar)
    VALUES (1, false, true, true, true);


INSERT INTO public.boat(
    id, behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
    VALUES (1, 'Zbranjeno skakanje sa palube!', 0, 7, 2, 220, 'pecaroska oprema za sve na brodu', true, 50, 120, 'Kruzer na Dunavu', 3000, 'Najbolji kruzer na Dunavu! Sve musterije su maksimalno zadovoljne!', 'kruzer', 1, 1, null);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
    VALUES (4, 'assets/boat2.jpg', 1, null, null);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES (5, 'assets/boat3.jpg', 1, null, null);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES (6, 'assets/boat-inside1.jpg', 1, null, null);

INSERT INTO public.image(
    id, image_url, boat_id, fishing_adventure_id, house_id)
VALUES (7, 'assets/boat-inside2.jpg', 1, null, null);

-- INSERT INTO public.addiotional_services_boat(
--     additional_services_id, boat_id)
--     VALUES (3, 1);

-- INSERT INTO public.addiotional_services_boat(
--     additional_services_id, boat_id)
-- VALUES (4, 1);

-- INSERT INTO public.addiotional_services_boat(
--     additional_services_id, boat_id)
-- VALUES (5, 1);


INSERT INTO public.boat(
    id, behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
VALUES (2, 'Zbranjeno skakanje sa palube!', 0, 7, 2, 220, 'pecaroska oprema za sve na brodu', true, 50, 120, 'Kruzer na Mlavi', 3000, 'Najbolji kruzer na Dunavu! Sve musterije su maksimalno zadovoljne!', 'kruzer', 1, 1, null);

INSERT INTO public.boat(
    id, behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
VALUES (3, 'Zbranjeno skakanje sa palube!', 0, 7, 2, 220, 'pecaroska oprema za sve na brodu', true, 50, 120, 'Kruzer na Savi', 3000, 'Najbolji kruzer na Dunavu! Sve musterije su maksimalno zadovoljne!', 'kruzer', 1, 1, null);

INSERT INTO public.boat(
    id, behaviour_rules, cancalletion_fee, capacity, engine_number, engine_power, fishing_equipment, is_cancalletion_free, length, max_speed, name, price_per_day, promo_description, type, address_id, navigation_equipment_id, owner_id)
VALUES (4, 'Zbranjeno skakanje sa palube!', 0, 7, 2, 220, 'pecaroska oprema za sve na brodu', true, 50, 120, 'Kruzer na Moravi', 3000, 'Najbolji kruzer na Dunavu! Sve musterije su maksimalno zadovoljne!', 'kruzer', 1, 1, null);
