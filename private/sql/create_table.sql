create table IF NOT EXISTS VisitDetails (
    Srno INTEGER PRIMARY KEY AUTOINCREMENT,
    VisitedDate TEXT,
    DoctorName TEXT,
    HealType INTEGER,
	Reason TEXT,
	Medicine TEXT,
	amount REAL
) WITHOUT ROWID;