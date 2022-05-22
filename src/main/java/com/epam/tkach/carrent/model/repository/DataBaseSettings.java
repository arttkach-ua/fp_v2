package com.epam.tkach.carrent.model.repository;

public final class DataBaseSettings {
    public static final DataBaseSelector getCurrentDBSelector(){
        return DataBaseSelector.MY_SQL;
    }

    enum DataBaseSelector {
        MY_SQL {
            @Override
            public String toString() {
                return "MySQL";
            }
        },
        MS_SQL {
            @Override
            public String toString() {
                return "Microsoft SQL Server";
            }
        },
        ORACLE {
            @Override
            public String toString() {
                return "Oracle Database";
            }
        },
        POSTGRESS {
            @Override
            public String toString() {
                return "PostgreSQL";
            }
        }
    }
}
