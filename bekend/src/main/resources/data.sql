
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
VALUES ('teodora.maruna@gmail.com', 'Teodora', 0, true, false, false, 'Maruna', 0, '$2a$12$kVtJTeqlsm.MDQta4U/B0eJGf.tCrCINpiwni1.Z7HBRC3wcw8G/G', 0, '', 0642581473, 0, '', 'dovla1', 2, 2, 1);
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
VALUES ('Nema pusenja', 2000, 0, false, 'Vikendica na Kopaoniku', 2000, 'Odlicna vikendica', 1, 2, 2);
