# 🤑 LE-FLIP — Buy • Sell • Profit

**LE-FLIP** is a Java console application for resellers to **track sales, purchases, and profits** 

The app allows you to:

- Record **sales and purchases**
- View **ledgers** and transaction histories
- Generate **pre-defined reports** or perform **custom searches**
- Calculate **balances, money spent, money earned, and net profit** for different periods

---

## 📋 Home Menu

The Home Page provide 4 options:

| Option | Description |
|--------|-------------|
| 1 | Add Sale (Sell Item) |
| 2 | Add Purchase (Buy Item) |
| 3 | Ledger |
| 4 | Balance Summary |
| 0 | Exit |
 
  
---

### 1️⃣ Add Sale (Sell Item)

When adding a sale, the app prompts for:

- **Item Name** – What are you selling?
- **Amount** – Sale price
- **Description** – Optional details

**sample code to sale :**

```java
 while (!validAmount) { // loops until value is valid
            System.out.print("How much? Enter an amount ");
            try {
                amount = scanner.nextDouble();
                scanner.nextLine();
                validAmount = true;  // when input is valid this makes the loop exit

            } catch (InputMismatchException e) {
                System.out.println(bold+red+"Wrong Input! please enter an amount in $ "+reset);
                scanner.nextLine(); // clear invalid input entered
            }
        }
        // Ask for description input and handle wrong input for description
            System.out.print("what item are you selling  ");
            description = scanner.nextLine();
        // Ask for vendor input and handle wrong input for description
            System.out.print("To who(Vendor)??");
            vendor = scanner.nextLine();
}
```
### 1️⃣ Add Sale (Sell Item)

When adding a sale, the app prompts for:

- **Item Name** – What are you selling?
- **Amount** – Sale price
- **Description** – Optional details

