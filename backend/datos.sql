--
-- PostgreSQL database dump
--

\restrict mq5h8cdxToKjhGnGGcmEBheksNcWgVCGMwBgyboTa9IcRikxQyuCWqles3RmHBY

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: donaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.donaciones VALUES (1, 'Supermercados Plaza Vea', 'Arroz', 500.00, 'kg', '2025-03-01', '2026-03-01', 'DISTRIBUIDO', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.donaciones VALUES (2, 'Corporación Wong', 'Leche UHT', 200.00, 'litros', '2025-03-05', '2025-09-05', 'DISTRIBUIDO', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.donaciones VALUES (3, 'Banco de Alimentos Perú', 'Aceite vegetal', 150.00, 'litros', '2025-03-10', '2026-03-10', 'EN_PROCESO', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.donaciones VALUES (4, 'Backus S.A.', 'Agua mineral', 300.00, 'unidades', '2025-03-12', '2026-01-12', 'PENDIENTE', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.donaciones VALUES (5, 'Alicorp S.A.A.', 'Fideos', 400.00, 'kg', '2025-03-15', '2026-03-15', 'PENDIENTE', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.donaciones VALUES (6, 'Nestlé Perú', 'Leche en polvo', 80.00, 'kg', '2025-03-20', '2026-03-20', 'PENDIENTE', NULL, '2026-04-03 21:59:05.935171');


--
-- Data for Name: organizaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.organizaciones VALUES (1, 'Comedor Popular Santa Rosa', '20501234561', 'Comedor Popular', 'Jr. Los Olivos 123, SJL', '01-5671234', 'María Quispe', true, '2026-04-03 21:59:05.935171');
INSERT INTO public.organizaciones VALUES (3, 'ONG Manos Solidarias', '20501234563', 'ONG', 'Calle Las Flores 789, Miraflores', '01-4441122', 'Ana Torres', true, '2026-04-03 21:59:05.935171');
INSERT INTO public.organizaciones VALUES (4, 'Wawa Wasi Los Pinos', '20501234564', 'Wawa Wasi', 'Jr. Huancayo 321, Ate', '01-3561890', 'Rosa Cáceres', true, '2026-04-03 21:59:05.935171');
INSERT INTO public.organizaciones VALUES (5, 'Centro Comunitario El Agustino', '20501234565', 'Centro Comunitario', 'Av. Prolongación Iquitos 100, El Agustino', '01-3270011', 'Juan Paredes', true, '2026-04-03 21:59:05.935171');
INSERT INTO public.organizaciones VALUES (2, 'Albergue San Francisco', '20501234562', 'Albergue', 'Av. Grau 456, Lima', '01-4231567', 'Carlos Mendoza', false, '2026-04-03 21:59:05.935171');


--
-- Data for Name: distribuciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.distribuciones VALUES (1, 1, 1, 200.00, '2025-03-03', 'ENTREGADO', 'Entrega completada sin inconvenientes', '2026-04-03 21:59:05.935171');
INSERT INTO public.distribuciones VALUES (2, 1, 2, 150.00, '2025-03-04', 'ENTREGADO', 'Recibido por el responsable del albergue', '2026-04-03 21:59:05.935171');
INSERT INTO public.distribuciones VALUES (3, 1, 3, 150.00, '2025-03-05', 'ENTREGADO', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.distribuciones VALUES (4, 2, 1, 100.00, '2025-03-07', 'ENTREGADO', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.distribuciones VALUES (5, 2, 4, 60.00, '2025-03-08', 'ENTREGADO', NULL, '2026-04-03 21:59:05.935171');
INSERT INTO public.distribuciones VALUES (6, 3, 2, 75.00, '2025-03-12', 'PENDIENTE', 'Pendiente confirmación de recepción', '2026-04-03 21:59:05.935171');
INSERT INTO public.distribuciones VALUES (7, 3, 5, 75.00, '2025-03-14', 'PENDIENTE', NULL, '2026-04-03 21:59:05.935171');


--
-- Name: distribuciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.distribuciones_id_seq', 7, true);


--
-- Name: donaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.donaciones_id_seq', 6, true);


--
-- Name: organizaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.organizaciones_id_seq', 5, true);


--
-- PostgreSQL database dump complete
--

\unrestrict mq5h8cdxToKjhGnGGcmEBheksNcWgVCGMwBgyboTa9IcRikxQyuCWqles3RmHBY

