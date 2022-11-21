# IP-Projekt

Projekt powstał w formie prostego systemu firmy kurierskiej opartej o bazę danych. Do wykonania projektu zostały wykorzystane podstawowe wzorce projektowe, takie jak Singleton, Metoda Fabrykująca, Pyłek, Dekorator czy Iterator. Sam system natomiast opiera się o następujące zależności:

Jeżeli użytkownik ma zamiar dostać się do systemu, musi najpierw wprowadzić odpowiedni login i hasło, pasujące do tych
zapisanych w bazie danych. Każdy pracownik ma przypisany konkretny login i hasło
wraz ze stanowiskiem, dzieki czemu każdy rodzaj pracownika ma zupełnie inne
funkcjonalności. Najważniejszymi obiektami w systemie są paczki, cały system jest
oparty wokół ich struktury, rozsyłania, budowania czy dostarczania. Po dodaniu
paczki do systemu przez pracownika stacjonarnego, trafia ona do bazy danych oraz
tymczasowego schowka w systemie. Następnie dowolny kurier, może przy pomocy
systemu pobrać paczki z magazynu, dostarczyć, potwierdzić ich dostarczenie czy
przydzielić automatycznie pojazd na podstawie wyliczanej przez system kubatury
paczek. Automatycznie przydzielany jest pojazd o najmniejszej możliwej kubaturze z
dostępnych, ale o na tyle dużej pojemności by zmieściła się tam maksymalna liczba
paczek. System śledzi również pracę kurierów poprzez przyznawanie „Punktów”,
gdzie następnie trafiają one na konto kurierów w celu stworzenia listy rankingowej.
Jest możliwość tworzenia listy rankingowej miesięcznej oraz istnieją możliwości
wyświetlenia raportów zarobkowych firmy z miesiąca oraz z roku. Istnieje również
możliwość przyznania premii dowolnemu z pracowników, poprzez skorzystanie z
jednej z funkcji księgowego. Może on również zapisywać stan paczek do specjalej
tablicy tymczasowej oraz bazy danych w celu ich archiwizacji w sytuacji awarii oraz
zapisywać dane z niej prosto do obecnie obowiązującej bazy danych. Natomiast
oprócz tego baza sama zapisuje swój ostatni stan w chwili zamknięcia systemu.

## Funkcjonalności systemu

- Nadawanie paczek – Pracownik stacjonarny ma możliwość nadania paczki w systemie z
pełnym procesem jej parametryzacji, a oprócz tego pozwala na tworzenie nowych rodzajów
paczek.

- Przypisywanie paczek do Kuriera – Kurier po zalogowaniu się do systemu ma możliwość
pobrania listy paczek dostępnych w tym momęcie z magazynu do przekazania. Lista paczek
jest generowana tak aby nie przekroczyć maksymalnej kubatury pojazdu aktualnie
znajdującego się w systemie. Pojazd jest przydzielany tak aby paczki zajmowały jak
największą procentową jego pojemność (Patrz niżej)

- Automatyczny dobór odpowiedniego pojazdu na podstawie wyliczonej kubatury – Na
podstawie wyliczonej kubatury pobranych przez kuriera paczek, przydzielany jest mu
pojazd o możliwie najmniejszej pojemności natomiast na tyle dużej by pomieścić wszystkie
z nich.

- Listowanie paczek – Kurier ma możliwość podglądu informacji o paczkach które zostały do
niego przypisane oraz jaki jest ich status wraz z typem.

- Zgłoszenie dostarczenia paczki – Kurier zwraca informacje do systemu, że dana paczka
została dostarczona.

- Ranking miesięczny – System tworzy ranking wszystkich kurierów gdzie kurierzy są
uporządkowani od kuriera który otrzymał największą ilość „punktów”.

- Generowanie raportu miesięcznego i rocznego – System generuje miesięczny raport, w
którym są zamieszczane wszystkie zarobki oraz wydatki firmy z danego miesiąca lub roku.

- Możliwość przyznawania premii – Księgowy ma opcje przynzania premii w wybranej
kwocie pozostałym pracownikom.

- Możliwość zapisu na bazie historii stanów paczek – W przypadku awarii, system zapisuje
wszystkie informacje o paczkach w zapasowej tabeli tymczasowej oraz bazie danych. Taką
samą możliwość ma również księgowy, poprzez wybranie jednej z funkcji może zapisać stan
systemu paczek do bazy.

- Możliwość wczytania do bazy historii stanów paczek – System ma możliwość wczytania
danych z zapasowej bazy w sytuacji awarii.

## Biblioteki

- Java 

- Oracle MySQL

- JDBC / MySQL connector

- Java SQL

- JUnit 5
