
INSERT INTO public.authority (name) VALUES ( 'ROLE_HOUSE_OWNER');
INSERT INTO public.authority (name) VALUES ( 'ROLE_BOAT_OWNER');
INSERT INTO public.authority (name) VALUES ( 'ROLE_USER');
INSERT INTO public.authority ( name) VALUES ( 'ROLE_INSTRUCTOR');
INSERT INTO public.authority ( name) VALUES ( 'ROLE_ADMINISTRATOR');


INSERT INTO public.user_category (discount_percentage, name, points, version) VALUES ( 1, 'basic', 1, 1);
INSERT INTO public.company (id, percentage__per_reservation, points_reservation_client, points_reservation_owner) VALUES (1, 1, 1, 1);
