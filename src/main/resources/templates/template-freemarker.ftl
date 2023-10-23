<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css" integrity="sha512-wnea99uKIC3TJF7v4eKk4Y+lMz2Mklv18+r4na2Gn1abDRPPOeef95xTzdwGD9e6zXJBteMIhZ1+68QC5byJZw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<p>Kính gửi quý khách <span style="font-style: italic; font-size: 20px; font-weight: 600; color: blue;">${username}</span></p>
<p>Chúng tôi xin cảm ơn và gửi quý khách thông tin hoá đơn như sau:</p>
<div style="padding-top: 20px; padding-bottom: 20px; background-color: #f0f0f0;">
    <div style="margin: 0 auto; padding-left: 16px; padding-right: 16px;">
        <div style="padding-top: 3px; padding-bottom: 2px; margin-top: 6px; margin-bottom: 5px; font-style: italic; font-weight: bold; line-height: 1.5; text-align: center; border-radius: 16px; width: 50%; margin: 0 auto;">
            <h2 style="margin-bottom: 3px; font-size: 24px; font-weight: bold; color: #333;">THÔNG TIN HOÁ ĐƠN</h2>
        </div>
        <div style="height: 250mm; width: 210mm; padding: 48px 0 48px 128px; border: 1px solid #333; margin: 0 auto;">
            <div style="display: flex; justify-content: space-around;">
                <div>
                    <p style="font-size: 32px; padding-bottom: 16px;">Carve - Khắc Kiến Thức</p>
                    <p style="font-size: 14px; color: #888;">209 Văn Tiến Dũng - Đà Nẵng - Việt Nam</p>
                    <p style="font-size: 14px; color: #888;">mrhuyleCarve@gmail.com</p>
                </div>
                <div>
                    <img style="width: 80px; height: 80px;" src="https://res.cloudinary.com/dkkiwh0al/image/upload/v1697679372/fmimskay8ddudoxx6nsx.png" />
                </div>
            </div>
            <div style="display: flex; justify-content: space-around; padding-top: 64px;">
                <div>
                    <p style="font-size: 32px; font-weight: bold;">HÓA ĐƠN</p>
                    <p style="font-size: 14px; font-weight: bold;">KHÁCH HÀNG: <span style="font-weight: normal;">${username}</span></p>
                    <p style="font-size: 14px; font-weight: bold;">SỐ HĐ: <span style="font-weight: normal;">${code}</span></p>
                    <p style="font-size: 14px; font-weight: bold;">NGÀY HĐ: <span style="font-weight: normal;">${date}</span></p>
                </div>
            </div>
            <div style="padding-top: 64px;">
                <table style="width: 90%; font-size: 14px; border-collapse: collapse;">
                    <thead style="border-bottom: 2px solid #000;">
                    <tr style="height: 40px; text-align: left;">
                        <th>BỘ THẺ</th>
                        <th>GIÁ</th>
                        <th>KHUYẾN MÃI</th>
                        <th style="text-align: right;">TỔNG CỘNG</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orderItems as item>
                        <tr style="height: 40px;">
                            <#assign actualPriceFormatted = (item.deckPrice * (100 - item.promoPercent) / 100)>
                            <td>${item.deckName}</td>
                            <td>${item.deckPrice}</td>
                            <td style="text-align: center;">${item.promoPercent} %</td>
                            <td style="text-align: right;">${actualPriceFormatted}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div style="position: relative; width: 90%">
                <div style="display: flex; align-items: center; justify-content: space-between; width: 100%; gap: 16px; font-size: 20px; font-weight: bold; color: #888;">
                    <span>Tạm tính:   </span>
                    <span>${preTotal}</span>
                </div>
                <div style="display: flex; align-items: center; justify-content: space-between; width: 100%; gap: 16px; font-size: 20px; font-weight: bold; color: #f00;">
                    <span>Ưu đãi:   </span>
                    <span>- ${discount} %</span>
                </div>
                <div style="display: flex; align-items: center; justify-content: space-between; width: 100%; gap: 16px; font-size: 20px; font-weight: bold; color: #000;">
                    <span>TỔNG:   </span>
                    <span>${total}</span>
                </div>
            </div>
            <div style="font-size: 14px; font-style: italic; text-align: left; color: #f00;">
                * Hóa đơn điện tử đã được gửi về email của quý khách
            </div>
        </div>
    </div>
</div>
</body>
</html>