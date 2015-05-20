#!/bin/sh
psql -U uaiadmin --host localhost uaisoccer -c 'delete from team;'
psql -U uaiadmin --host localhost uaisoccer -c 'ALTER SEQUENCE public.hibernate_sequence RESTART WITH 1;'
