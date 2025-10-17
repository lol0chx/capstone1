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

**Example Java Code to Record a Sale:**

```java
Transaction sale = TransactionInputHelper.getTransactionInput("sale");
if(sale != null) {
    TransactionHandler.saveTransaction(sale);
    System.out.println("\n✅ Sale recorded successfully!");
}
