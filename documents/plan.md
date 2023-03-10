## План автоматизации тестирования покупки туристической путевки 


### Перечень автоматизированных сценариев

**Валидные значения для полей**

|Поле|Значение|
|----------|-----------|
|Номер одобренной карты|4444 4444 4444 4441| 
|Номер отклоненной карты|4444 4444 4444 4442|


**Позитивные сценарии**

|№         |Тест-кейс  |Ожидаемый результат|
|----------|-----------|------------|
|1|Приобретение тура с использованием дебетовой карты, введение корректных данных|Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком". Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных|
|2|Приобретение тура в кредит, введение корректных данных  |Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком". Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных|

**Негативные сценарии сценарии**

+ Страница приобретения тура с использованием дебетовой карты

|№         |Тест-кейс  |Ожидаемый результат|
|----------|-----------|------------|
|3|Ввод данных ранее отклоненной карты номер 4444 4444 4444 4442|Появляется выплывающее окно с сообщением "Ошибка! Карта отклонена банком". Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных|
|4|Оставить все поля пустыми, нажать кнопку “Продолжить”|Появляются сообщения о том, что все поля обязательны для заполнения |
|5|Заполнить все поля корректными данными кроме поля “Номер карты”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о неверном формате|
|6|Заполнить все поля корректными данными кроме поля “Месяц”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о неверном формате |
|7|Заполнить все поля корректными данными кроме поля “Год”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение неверном формате |
|8|Заполнить все поля корректными данными кроме поля “Владелец”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о том, что поле обязательно для заполнения |
|9|Заполнить все поля корректными данными кроме поля “CVC/CVV”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о неверном формате|
|10|Заполнить поле “Номер карты” - ввести 15 цифр “4444 4444 4444 444”|Появляется сообщение “Неверный формат”|
|11|Заполнить поле “Номер карты” - ввести 17 цифр “4444 4444 4444 44411”|Поле заполняется первыми 16ю цифрами, 17я обрезается|
|12|Заполнить поле “Номер карты” - ввести буквы “IVANOV”|Появляется сообщение “Неверный формат”|
|13|Заполнить поле “Номер карты” - ввести 16 нулей “0000000000000000”|Появляется сообщение “Неверный формат”|
|14|Заполнить поле “Номер карты” - ввести  спецсимволы “4444 4444 4444 44!!”|Появляется сообщение “Неверный формат”|
|15|Заполнить поле “Месяц” - ввести “0”|Появляется сообщение “Неверный формат”|
|16|Заполнить поле “Месяц” - ввести “00”|Появляется сообщение “Неверный формат”|
|17|Заполнить поле “Месяц” - ввести “13”|Появляется сообщение “Неверно указан срок действия карты”|
|18|Заполнить поле “Месяц” - ввести буквы “MM”|Поле “Месяц” не заполняется|
|19|Заполнить поле “Месяц” - ввести спецсимволы “!!”|Поле “Месяц” не заполняется|
|20|Заполнить поле “Год” - ввести уже прошедший год |Появляется сообщение “Истек срок действия карты”|
|21|Заполнить поле “Год” - ввести “0”|Появляется сообщение “Неверный формат”|
|22|Заполнить поле “Год” - ввести  ввести буквы “MM”|Поле “Год” не заполняется|
|23|Заполнить поле “Год” -  ввести спецсимволы “!!”|Поле “Год” не заполняется|
|24|Заполнить поле “Владелец” -  ввести 30 символов “IVANIVANOVIVANIVANOVIVANIVANOV”|Появляется сообщение “Неверный формат”|
|25|Заполнить поле “Владелец” -  ввести 1 символ “V”|Появляется сообщение “Неверный формат”|
|26|Заполнить поле “Владелец” -  ввести имя на кириллице “ИВАН ИВАНОВ”|Поле “Владелец” не заполняется|
|27|Заполнить поле “Владелец” -  ввести спецсимволы “!!”|Поле “Владелец” не заполняется|
|28|Заполнить поле “Владелец” -  ввести цифры 11”|Поле “Владелец” не заполняется|
|29|Заполнить поле “CVC/CVV” -  ввести “000”|Появляется сообщение “Неверный формат”|
|30|Заполнить поле “CVC/CVV” -  ввести 4 цифры “2354”|Поле заполняется первыми 3мя цифрами, 4я обрезается|
|31|Заполнить поле “CVC/CVV” -  ввести буквы “AAA”|Поле “CVC/CVV” не заполняется|
|32|Заполнить поле “CVC/CVV” -  спецсимволы  “!!!”|Поле “CVC/CVV” не заполняется|

+ Страница приобретения тура в кредит

|№         |Тест-кейс  |Ожидаемый результат|
|----------|-----------|------------|
|33|Ввод данных ранее отклоненной карты номер 4444 4444 4444 4442|Появляется выплывающее окно с сообщением "Ошибка! Карта отклонена банком". Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных|
|34|Оставить все поля пустыми, нажать кнопку “Продолжить”|Появляются сообщения о том, что все поля обязательны для заполнения |
|35|Заполнить все поля корректными данными кроме поля “Номер карты”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о неверном формате|
|36|Заполнить все поля корректными данными кроме поля “Месяц”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о неверном формате |
|37|Заполнить все поля корректными данными кроме поля “Год”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение неверном формате |
|38|Заполнить все поля корректными данными кроме поля “Владелец”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о том, что поле обязательно для заполнения |
|39|Заполнить все поля корректными данными кроме поля “CVC/CVV”, его оставить пустым. Нажать кнопку “Продолжить”|Появляется сообщение о неверном формате|
|40|Заполнить поле “Номер карты” - ввести 15 цифр “4444 4444 4444 444”|Появляется сообщение “Неверный формат”|
|41|Заполнить поле “Номер карты” - ввести 17 цифр “4444 4444 4444 44411”|Поле заполняется первыми 16ю цифрами, 17я обрезается|
|42|Заполнить поле “Номер карты” - ввести буквы “IVANOV”|Появляется сообщение “Неверный формат”|
|43|Заполнить поле “Номер карты” - ввести 16 нулей “0000000000000000”|Появляется сообщение “Неверный формат”|
|44|Заполнить поле “Номер карты” - ввести  спецсимволы “4444 4444 4444 44!!”|Появляется сообщение “Неверный формат”|
|45|Заполнить поле “Месяц” - ввести “0”|Появляется сообщение “Неверный формат”|
|46|Заполнить поле “Месяц” - ввести “00”|Появляется сообщение “Неверный формат”|
|47|Заполнить поле “Месяц” - ввести “13”|Появляется сообщение “Неверно указан срок действия карты”|
|48|Заполнить поле “Месяц” - ввести буквы “MM”|Поле “Месяц” не заполняется|
|49|Заполнить поле “Месяц” - ввести спецсимволы “!!”|Поле “Месяц” не заполняется|
|50|Заполнить поле “Год” - ввести уже прошедший год |Появляется сообщение “Истек срок действия карты”|
|51|Заполнить поле “Год” - ввести “0”|Появляется сообщение “Неверный формат”|
|52|Заполнить поле “Год” - ввести  ввести буквы “MM”|Поле “Год” не заполняется|
|53|Заполнить поле “Год” -  ввести спецсимволы “!!”|Поле “Год” не заполняется|
|54|Заполнить поле “Владелец” -  ввести 30 символов “IVANIVANOVIVANIVANOVIVANIVANOV”|Появляется сообщение “Неверный формат”|
|55|Заполнить поле “Владелец” -  ввести 1 символ “V”|Появляется сообщение “Неверный формат”|
|56|Заполнить поле “Владелец” -  ввести имя на кириллице “ИВАН ИВАНОВ”|Поле “Владелец” не заполняется|
|57|Заполнить поле “Владелец” -  ввести спецсимволы “!!”|Поле “Владелец” не заполняется|
|58|Заполнить поле “Владелец” -  ввести цифры 11”|Поле “Владелец” не заполняется|
|59|Заполнить поле “CVC/CVV” -  ввести “000”|Появляется сообщение “Неверный формат”|
|60|Заполнить поле “CVC/CVV” -  ввести 4 цифры “2354”|Поле заполняется первыми 3мя цифрами, 4я обрезается|
|61|Заполнить поле “CVC/CVV” -  ввести буквы “AAA”|Поле “CVC/CVV” не заполняется|
|62|Заполнить поле “CVC/CVV” -  спецсимволы  “!!!”|Поле “CVC/CVV” не заполняется


### Перечень используемых инструментов

- Git, GitHub - для хранения кода
- JUnit5 - для написания автотестов и их запуска
- Java 11 - язык написания автотестов
- Gradle - система управления проектами
- Selenide - фреймворк для автоматизированного тестирования веб-приложений
- Docker Compose - для запуска мультиконтейнерных приложений
- Allure - для создания отчета


### Перечень  и описание возможных рисков при автоматизации

- Сложности при подключении банковского стимулятора
- Ошибки внутри банковского стимулятора
- Отсутствие ТЗ, т.е. четкого понимания того, как приложение должно работать.

### Перечень  и описание возможных рисков при автоматизации

- Подготовка к написанию автотестов: 10 часов
- Написание автотестов: 30 часов
- Подготовка отчетов: 8 часа


