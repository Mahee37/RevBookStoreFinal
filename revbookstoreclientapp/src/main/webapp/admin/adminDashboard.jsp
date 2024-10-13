<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Dashboard" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4 text-center display-4" style="font-weight: 700; letter-spacing: 2px;">Dashboard Overview</h1>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">
            <strong>Error:</strong> ${error}
        </div>
    </c:if>

    <!-- Cards Section -->
    <div class="row text-center mb-4">
        <c:set var="buyersCount" value="${noofcustomer != null ? noofcustomer : 0}" />
        <c:set var="sellersCount" value="${noofproduct != null ? noofproduct : 0}" />
        <c:set var="complaintsCount" value="${noofcomplaint != null ? noofcomplaint : 0}" />
        <c:set var="ordersCount" value="${nooforder != null ? nooforder : 0}" />

        <div class="col-md-3">
            <div class="card mb-4" style="border-radius: 15px;">
                <div class="card-body">
                    <h5 class="card-title">Total Buyers</h5>
                    <p class="card-text">${buyersCount > 0 ? buyersCount : 'No data available'}</p>
                    <a href="${pageContext.request.contextPath}/admin/viewBuyers" class="btn btn-dark">View Buyers</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card mb-4" style="border-radius: 15px;">
                <div class="card-body">
                    <h5 class="card-title">Total Sellers</h5>
                    <p class="card-text">${sellersCount > 0 ? sellersCount : 'No data available'}</p>
                    <a href="${pageContext.request.contextPath}/admin/viewSellers" class="btn btn-dark">View Sellers</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card mb-4" style="border-radius: 15px;">
                <div class="card-body">
                    <h5 class="card-title">Total Complaints</h5>
                    <p class="card-text">${complaintsCount > 0 ? complaintsCount : 'No data available'}</p>
                    <a href="${pageContext.request.contextPath}/admin/viewComplaints" class="btn btn-dark">View Complaints</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card mb-4" style="border-radius: 15px;">
                <div class="card-body">
                    <h5 class="card-title">Total Orders</h5>
                    <p class="card-text">${ordersCount > 0 ? ordersCount : 'No data available'}</p>
                    <a href="${pageContext.request.contextPath}/admin/viewOrders" class="btn btn-dark">View Orders</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Charts Section -->
    <div class="row">
        <div class="col-md-6 chart-container">
            <canvas id="buyersChart"></canvas>
        </div>
        <div class="col-md-6 chart-container">
            <canvas id="sellersChart"></canvas>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 chart-container">
            <canvas id="complaintsChart"></canvas>
        </div>
        <div class="col-md-6 chart-container">
            <canvas id="ordersChart"></canvas>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const buyersCount = ${buyersCount};
    const sellersCount = ${sellersCount};
    const complaintsCount = ${complaintsCount};
    const ordersCount = ${ordersCount};

    const createSpikeChart = (ctx, label, data, color) => {
        return new Chart(ctx, {
            type: 'bar',
            data: {
                labels: [label],
                datasets: [{
                    label: label,
                    data: [data],
                    backgroundColor: color,
                    borderWidth: 2,
                    barThickness: 60
                }]
            },
            options: {
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Counts'
                        },
                        ticks: {
                            autoSkip: false
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Categories'
                        },
                        beginAtZero: true
                    }
                },
                indexAxis: 'y',
            }
        });
    };

    // Call createSpikeChart for each category
    createSpikeChart(document.getElementById('buyersChart'), 'Buyers', buyersCount, 'rgba(75, 192, 192, 1)');
    createSpikeChart(document.getElementById('sellersChart'), 'Sellers', sellersCount, 'rgba(54, 162, 235, 1)');
    createSpikeChart(document.getElementById('complaintsChart'), 'Complaints', complaintsCount, 'rgba(255, 99, 132, 1)');
    createSpikeChart(document.getElementById('ordersChart'), 'Orders', ordersCount, 'rgba(255, 205, 86, 1)');
</script>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
