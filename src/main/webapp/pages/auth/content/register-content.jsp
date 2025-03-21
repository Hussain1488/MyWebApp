<%-- auth/content/register-content.jsp--%>
<div class="font-sans antialiased bg-grey-lightest">

    <!-- Content -->
    <div class="w-full bg-grey-lightest" style="padding-top: 4rem;">
        <div class="container mx-auto py-8">
            <div class="w-5/6 lg:w-1/2 mx-auto bg-white rounded shadow">
                <div class="py-4 px-8 text-black text-xl border-b border-grey-lighter">Register for a free account</div>
                <div class="py-4 px-8">
                    <div class="flex mb-4">
                        <div class="w-1/2 mr-1">
                            <label class="block text-grey-darker text-sm font-bold mb-2" for="first_name">First Name</label>
                            <input class="appearance-none border rounded w-full py-2 px-3 text-grey-darker" id="first_name" type="text" placeholder="Your first name">
                        </div>
                        <div class="w-1/2 ml-1">
                            <label class="block text-grey-darker text-sm font-bold mb-2" for="last_name">Last Name</label>
                            <input class="appearance-none border rounded w-full py-2 px-3 text-grey-darker" id="last_name" type="text" placeholder="Your last name">
                        </div>
                    </div>
                    <div class="mb-4">
                        <label class="block text-grey-darker text-sm font-bold mb-2" for="email">Email Address</label>
                        <input class="appearance-none border rounded w-full py-2 px-3 text-grey-darker" id="email" type="email" placeholder="Your email address">
                    </div>
                    <div class="mb-4">
                        <label class="block text-grey-darker text-sm font-bold mb-2" for="password">Password</label>
                        <input class="appearance-none border rounded w-full py-2 px-3 text-grey-darker" id="password" type="password" placeholder="Your secure password">
                        <p class="text-grey text-xs mt-1">At least 6 characters</p>
                    </div>
                    <div class="flex items-center justify-between mt-8">
                        <button class="bg-blue hover:bg-blue-dark text-white font-bold py-2 px-4 rounded-full" type="submit">
                            Sign Up
                        </button>
                    </div>
                </div>
            </div>
            <p class="text-center my-4">
                <a href="auth/login" class="text-grey-dark text-sm no-underline hover:text-grey-darker">I already have an account</a>
            </p>
        </div>
    </div>
    <!-- Footer -->
    <footer class="w-full bg-grey-lighter py-8">
        <div class="container mx-auto text-center px-8">
            <p class="text-grey-dark mb-2 text-sm">This is a product of <span class="font-bold">Your Company</span></p>
        </div>
    </footer>
</div>